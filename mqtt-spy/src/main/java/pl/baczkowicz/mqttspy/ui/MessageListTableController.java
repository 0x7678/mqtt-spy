package pl.baczkowicz.mqttspy.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.baczkowicz.mqttspy.connectivity.MqttContent;
import pl.baczkowicz.mqttspy.connectivity.messagestore.ObservableMessageStore;
import pl.baczkowicz.mqttspy.events.EventManager;
import pl.baczkowicz.mqttspy.events.observers.MessageIndexChangeObserver;
import pl.baczkowicz.mqttspy.ui.properties.MqttContentProperties;
import pl.baczkowicz.mqttspy.ui.utils.Utils;

public class MessageListTableController implements Initializable, MessageIndexChangeObserver
{
	final static Logger logger = LoggerFactory.getLogger(MessageListTableController.class);
	
	private ObservableList<MqttContentProperties> items; 
	
	@FXML
	private TableView<MqttContentProperties> messageTable;

	@FXML
	private TableColumn<MqttContentProperties, String> messageTopicColumn;
	
	@FXML
	private TableColumn<MqttContentProperties, String> messageContentColumn;

	@FXML
	private TableColumn<MqttContentProperties, String> messageReceivedAtColumn;

	private ObservableMessageStore store;

	private EventManager eventManager;

	public void initialize(URL location, ResourceBundle resources)
	{				
		// Table
		messageTopicColumn.setCellValueFactory(new PropertyValueFactory<MqttContentProperties, String>(
				"topic"));

		messageContentColumn
				.setCellValueFactory(new PropertyValueFactory<MqttContentProperties, String>(
						"lastReceivedPayload"));

		messageReceivedAtColumn.setCellValueFactory(new PropertyValueFactory<MqttContentProperties, String>("lastReceivedTimestamp"));
		messageReceivedAtColumn.setCellFactory(new Callback<TableColumn<MqttContentProperties, String>, TableCell<MqttContentProperties, String>>()
		{
			public TableCell<MqttContentProperties, String> call(
					TableColumn<MqttContentProperties, String> param)
			{
				final TableCell<MqttContentProperties, String> cell = new TableCell<MqttContentProperties, String>()
				{
					@Override
					public void updateItem(String item, boolean empty)
					{
						super.updateItem(item, empty);
						if (!isEmpty())
						{
							setText(item.toString());
						}
						else
						{
							setText(null);
						}
					}
				};
				cell.setAlignment(Pos.TOP_CENTER);
				
				return cell;
			}
		});
		
		messageTable.setOnMouseClicked(new EventHandler<Event>()
				{
					@Override
					public void handle(Event event)
					{
						selectItem();
					}
				});
		
		messageTable
				.setRowFactory(new Callback<TableView<MqttContentProperties>, TableRow<MqttContentProperties>>()
				{
					public TableRow<MqttContentProperties> call(
							TableView<MqttContentProperties> tableView)
					{
						final TableRow<MqttContentProperties> row = new TableRow<MqttContentProperties>()
						{
							@Override
							protected void updateItem(MqttContentProperties item, boolean empty)
							{
								super.updateItem(item, empty);
								if (!isEmpty() && item.getSubscription() != null)
								{
									
									this.setStyle(Utils.createBgRGBString(item.getSubscription()
											.getColor(), getIndex() % 2 == 0 ? 0.8 : 0.6)
											+ " -fx-background-radius: 6; ");
								}
								else
								{
									this.setStyle(null);
								}
							}
						};

						return row;
					}
				});
	}
	
	private void selectItem()
	{
		final MqttContentProperties item = messageTable.getSelectionModel().getSelectedItem();
		if (item != null)
		{
			final Object[] array = store.getMessages().toArray();
			for (int i = 0; i < store.getMessages().size(); i++)
			{
				if (((MqttContent) array[i]).getId() == item.getId())
				{
					// logger.info("{} Changing selection to " + (array.length - i), store.getName());
					eventManager.changeMessageIndex(store, this, array.length - i);
				}
			}

		}
	}

	@Override
	public void onMessageIndexChange(int messageIndex)
	{
		if (store.getMessages().size() > 0)
		{
			final long id = ((MqttContent) store.getMessages().toArray()[store.getMessages().size()
					- messageIndex]).getId();

			for (final MqttContentProperties item : items)
			{
				if (item.getId() == id)
				{
					if (!item.equals(messageTable.getSelectionModel().getSelectedItem()))
					{
						messageTable.getSelectionModel().select(item);
						break;
					}
				}
			}
		}
		
	}
	
	public void init()
	{
		messageTable.setContextMenu(createMessageListTableContextMenu(messageTable));
		messageTable.setItems(items);	
	}

	public void setEventManager(final EventManager eventManager)
	{
		this.eventManager = eventManager;
	}
	
	public void setItems(final ObservableList<MqttContentProperties> items)
	{
		this.items = items;
	}
	
	public void setStore(final ObservableMessageStore store)
	{
		this.store = store;
	}
	
	public static ContextMenu createMessageListTableContextMenu(final TableView<MqttContentProperties> messageTable)
	{
		final ContextMenu contextMenu = new ContextMenu();
		
		// Copy topic
		final MenuItem copyTopicItem = new MenuItem("[Topic] Copy to clipboard");
		copyTopicItem.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				final MqttContentProperties item = messageTable.getSelectionModel()
						.getSelectedItem();
				if (item != null)
				{
					final ClipboardContent content = new ClipboardContent();
					content.putString(item.topicProperty().getValue());
					Clipboard.getSystemClipboard().setContent(content);
				}
			}
		});
		contextMenu.getItems().add(copyTopicItem);

		// Separator
		contextMenu.getItems().add(new SeparatorMenuItem());
		
		// Copy content
		final MenuItem copyContentItem = new MenuItem("[Content] Copy to clipboard");
		copyContentItem.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				final MqttContentProperties item = messageTable.getSelectionModel()
						.getSelectedItem();
				if (item != null)
				{
					final ClipboardContent content = new ClipboardContent();
					content.putString(item.lastReceivedPayloadProperty().getValue());
					Clipboard.getSystemClipboard().setContent(content);
				}
			}
		});
		contextMenu.getItems().add(copyContentItem);
		
		// Separator
		// contextMenu.getItems().add(new SeparatorMenuItem());
		//
		// // Show message filters
		// final MenuItem showMessageItem = new MenuItem("Preview message");
		// showMessageItem.setOnAction(new EventHandler<ActionEvent>()
		// {
		// public void handle(ActionEvent e)
		// {
		// final ObservableMqttContent item = messageTable.getSelectionModel()
		// .getSelectedItem();
		// if (item != null)
		// {
		// navigationEventDispatcher.dispatchEvent(new MessageIndexChangedEvent(
		// messageTable.getSelectionModel().getSelectedIndex()));
		// }
		// }
		// });
		// contextMenu.getItems().add(showMessageItem);

		return contextMenu;
	}
}
