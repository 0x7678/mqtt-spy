package pl.baczkowicz.mqttspy.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.baczkowicz.mqttspy.configuration.generated.ConversionMethod;
import pl.baczkowicz.mqttspy.configuration.generated.FormatterDetails;
import pl.baczkowicz.mqttspy.connectivity.MqttSubscription;
import pl.baczkowicz.mqttspy.events.EventManager;
import pl.baczkowicz.mqttspy.events.observers.ClearTabObserver;
import pl.baczkowicz.mqttspy.events.observers.SubscriptionStatusChangeObserver;
import pl.baczkowicz.mqttspy.stats.StatisticsManager;
import pl.baczkowicz.mqttspy.storage.ManagedMessageStoreWithFiltering;
import pl.baczkowicz.mqttspy.ui.connections.SubscriptionManager;
import pl.baczkowicz.mqttspy.ui.properties.RuntimeConnectionProperties;
import pl.baczkowicz.mqttspy.ui.utils.DialogUtils;
import pl.baczkowicz.mqttspy.ui.utils.FormattingUtils;
import pl.baczkowicz.mqttspy.ui.utils.Utils;

public class SubscriptionController implements Initializable, ClearTabObserver, SubscriptionStatusChangeObserver
{
	public MqttSubscription getSubscription()
	{
		return subscription;
	}

	final static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	private static final int MIN_EXPANDED_SUMMARY_PANE_HEIGHT = 130;

	private static final int MIN_COLLAPSED_SUMMARY_PANE_HEIGHT = 31;
	
	private static final String SUMMARY_PANE_TITLE = "Received messages summary";

	/** (10 topics; 50 messages, load average: 0.1/0.5/5.0). */
	private static final String SUMMARY_PANE_STATS_FORMAT = " (%s, %s, " + DialogUtils.STATS_FORMAT + ")";

	@FXML
	private SplitPane splitPane;

	@FXML
	private AnchorPane messagePane;

	/**
	 * The name of this field needs to be set to the name of the pane +
	 * Controller (i.e. <fx:id>Controller).
	 */
	@FXML
	private MessageNavigationController messageNavigationPaneController;
	
	/**
	 * The name of this field needs to be set to the name of the pane +
	 * Controller (i.e. <fx:id>Controller).
	 */
	@FXML
	private MessageController messagePaneController;
	
	/**
	 * The name of this field needs to be set to the name of the pane +
	 * Controller (i.e. <fx:id>Controller).
	 */
	@FXML
	private SubscriptionSummaryTableController summaryTablePaneController;

	@FXML
	private TitledPane summaryTitledPane;

	@FXML
	private ToggleGroup wholeMessageFormat;

	@FXML
	private MenuButton formattingMenuButton;

	@FXML
	private Menu formatterMenu;
	
	@FXML
	private Menu customFormatterMenu;

	@FXML
	private ToggleGroup selectionFormat;
	
	@FXML
	private ToggleButton searchButton;

	private ManagedMessageStoreWithFiltering store; 

	private Tab tab;

	private RuntimeConnectionProperties connectionProperties;

	private MqttSubscription subscription;

	private Stage searchStage;

	private SearchWindowController searchWindowController;

	private EventManager eventManager;

	// private StatisticsManager statisticsManager;

	// private Label summaryTitledPaneTitleLabel;

	private ConnectionController connectionController;

	// private Label titleLabel;

	private Label statsLabel;

	private AnchorPane paneTitle;

	private TextField searchBox;

	private HBox topicFilterBox;

	public SubscriptionController()
	{
		// eventDispatcher = new EventDispatcher();
		// eventDispatcher.addObserver(this);
	}
	
	@Override
	public void onClearTab(final ManagedMessageStoreWithFiltering subscription)
	{	
		messagePaneController.clear();
		messageNavigationPaneController.clear();
		
		// TODO: not sure what this is for...
		store.setAllShowValues(false, null);		
	}
	
	public void onSubscriptionStatusChanged(final MqttSubscription changedSubscription)
	{
		subscription = changedSubscription;
		updateContextMenu();
	}

	@FXML
	public void formatWholeMessage()
	{
		store.setFormatter((FormatterDetails) wholeMessageFormat.getSelectedToggle().getUserData());
	
		formattingMenuButton.setText(store.getFormatter().getName());
		eventManager.notifyFormatChanged(store);
		// eventDispatcher.dispatchEvent(new MessageFormatChangeEvent());
	}
	
	@FXML
	public void formatSelection()
	{
		final FormatterDetails messageFormat = (FormatterDetails) selectionFormat.getSelectedToggle().getUserData();
		
		if (messageFormat != null)
		{
			formattingMenuButton.setText("[Selection] " + messageFormat.getName());
		}
		else
		{
			formattingMenuButton.setText(((FormatterDetails) wholeMessageFormat.getSelectedToggle().getUserData()).getName());
		}
		messagePaneController.formatSelection(messageFormat);
	}

	public void updateMinHeights()
	{
		if (summaryTitledPane.isExpanded())
		{
			topicFilterBox.setVisible(true);
			summaryTitledPane.setMinHeight(MIN_EXPANDED_SUMMARY_PANE_HEIGHT);
		}
		else
		{
			topicFilterBox.setVisible(false);
			summaryTitledPane.setMinHeight(MIN_COLLAPSED_SUMMARY_PANE_HEIGHT);
			splitPane.setDividerPosition(0, 0.95);
		}
	}

	public void initialize(URL location, ResourceBundle resources)
	{			
		statsLabel = new Label();
		topicFilterBox = new HBox();
		topicFilterBox.setPadding(new Insets(0, 0, 0, 0));
		
		wholeMessageFormat.getToggles().get(0).setUserData(FormattingUtils.createBasicFormatter("default", 				"Plain", ConversionMethod.PLAIN));
		wholeMessageFormat.getToggles().get(1).setUserData(FormattingUtils.createBasicFormatter("default-hexDecoder", 	"HEX decoder", ConversionMethod.HEX_DECODE));
		wholeMessageFormat.getToggles().get(2).setUserData(FormattingUtils.createBasicFormatter("default-hexEncoder", 	"HEX encoder", ConversionMethod.HEX_ENCODE));
		wholeMessageFormat.getToggles().get(3).setUserData(FormattingUtils.createBasicFormatter("default-base64Decoder","Base64 decoder", ConversionMethod.BASE_64_DECODE));
		wholeMessageFormat.getToggles().get(4).setUserData(FormattingUtils.createBasicFormatter("default-base64Encoder","Base64 encoder", ConversionMethod.BASE_64_ENCODE));		
		
		wholeMessageFormat.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
		{
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
					Toggle newValue)
			{
				if (wholeMessageFormat.getSelectedToggle() != null)
				{
					formatWholeMessage();
				}
			}
		});
		
		selectionFormat.getToggles().get(0).setUserData(null);
		selectionFormat.getToggles().get(1).setUserData(FormattingUtils.createBasicFormatter("default", 				"Plain", ConversionMethod.PLAIN));
		selectionFormat.getToggles().get(2).setUserData(FormattingUtils.createBasicFormatter("default-hexDecoder", 		"HEX decoder", ConversionMethod.HEX_DECODE));
		selectionFormat.getToggles().get(3).setUserData(FormattingUtils.createBasicFormatter("default-hexEncoder", 		"HEX encoder", ConversionMethod.HEX_ENCODE));
		selectionFormat.getToggles().get(4).setUserData(FormattingUtils.createBasicFormatter("default-base64Decoder",	"Base64 decoder", ConversionMethod.BASE_64_DECODE));
		selectionFormat.getToggles().get(5).setUserData(FormattingUtils.createBasicFormatter("default-base64Encoder",	"Base64 encoder", ConversionMethod.BASE_64_ENCODE));				
		
		selectionFormat.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
		{
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
					Toggle newValue)
			{
				if (selectionFormat.getSelectedToggle() != null)
				{
					formatSelection();
				}
			}
		});

		summaryTitledPane.expandedProperty().addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2)
			{
				updateMinHeights();

			}
		});

		updateMinHeights();
	}
	
	public void init()
	{
		final Tooltip summaryTitledPaneTooltip = new Tooltip(
				"Load, the average number of messages per second, is calculated over the following intervals: " +  DialogUtils.getPeriodList() + ".");
		
		eventManager.registerClearTabObserver(this, store);
		
		// final Label summaryTitledPaneTitleLabel = new Label();
		// summaryTitledPaneTitleLabel.setTooltip(summaryTitledPaneTooltip);		
		
		summaryTablePaneController.setStore(store);
		summaryTablePaneController.setConnectionController(connectionController);
		summaryTablePaneController.setEventManager(eventManager);
		summaryTablePaneController.init();
		
		messagePaneController.setStore(store);
		messagePaneController.init();
		// The search pane's message browser wants to know about changing indices and format
		eventManager.registerChangeMessageIndexObserver(messagePaneController, store);
		eventManager.registerFormatChangeObserver(messagePaneController, store);
		
		messageNavigationPaneController.setStore(store);
		messageNavigationPaneController.setEventManager(eventManager);
		messageNavigationPaneController.init();		
		
		// The subscription pane's message browser wants to know about show first, index change and update index events 
		eventManager.registerChangeMessageIndexObserver(messageNavigationPaneController, store);
		eventManager.registerChangeMessageIndexFirstObserver(messageNavigationPaneController, store);
		eventManager.registerIncrementMessageIndexObserver(messageNavigationPaneController, store);
		
		eventManager.registerMessageAddedObserver(messageNavigationPaneController, store.getMessageList());
		eventManager.registerMessageRemovedObserver(messageNavigationPaneController, store.getMessageList());
		
		if (connectionProperties != null && connectionProperties.getFormatter() != null)
		{
			customFormatterMenu.setDisable(false);
			final RadioMenuItem customFormatterMenuItem = new RadioMenuItem(connectionProperties.getFormatter().getName());
			customFormatterMenuItem.setToggleGroup(wholeMessageFormat);			
			// TODO: check if this is really a custom one
			customFormatterMenuItem.setUserData(connectionProperties.getFormatter());
			customFormatterMenuItem.setSelected(true);
			customFormatterMenu.getItems().add(customFormatterMenuItem);
		}
		
		store.setFormatter((FormatterDetails) wholeMessageFormat.getSelectedToggle().getUserData());	
		
		paneTitle = new AnchorPane();
		paneTitle.setPadding(new Insets(0, 0, 0, 0));
		paneTitle.setMaxWidth(Double.MAX_VALUE);
		
		
		searchBox = new TextField();
		searchBox.setFont(new Font("System", 11));
		searchBox.setPadding(new Insets(2, 5, 2, 5));
		searchBox.setMaxWidth(100);
		searchBox.textProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, final String oldValue, final String newValue)
			{
				summaryTablePaneController.updateTopicFilter(newValue);			
			}
		});
		
		paneTitle = new AnchorPane();
		final HBox titleBox = new HBox();
		titleBox.setPadding(new Insets(0, 0, 0, 0));		
		topicFilterBox.getChildren().addAll(new Label(" [filter topics: "), searchBox, new Label("] "));
		titleBox.getChildren().addAll(new Label(SUMMARY_PANE_TITLE), topicFilterBox);
		titleBox.prefWidth(Double.MAX_VALUE);
		
		paneTitle.setPadding(new Insets(0, 0, 0, 0));
		summaryTitledPane.widthProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{				 										
				Platform.runLater(new Runnable()
				{					
					@Override
					public void run()
					{
						updateTitleWidth(40);	
					}
				});
			}
		});
		
		//HBox.setHgrow(statsLabel, Priority.ALWAYS);
		paneTitle.getChildren().addAll(titleBox, statsLabel);
		
		summaryTitledPane.setText(null);
		summaryTitledPane.setGraphic(paneTitle);
		
		statsLabel.setTextAlignment(TextAlignment.RIGHT);
		statsLabel.setAlignment(Pos.CENTER_RIGHT);
		statsLabel.setTooltip(summaryTitledPaneTooltip);
		AnchorPane.setRightAnchor(statsLabel, 5.0);
		
		//summaryTitledPane.setText(null);
		//summaryTitledPane.setGraphic(summaryTitledPaneTitleLabel);
		
		updateSubscriptionStats();
		
		// logger.info("init(); finished on SubscriptionController");
	}
	
	private void updateTitleWidth(final int value)
	{
		double width = summaryTitledPane.getWidth();
		
		if (summaryTitledPane.getScene() != null)			
		{
			if (summaryTitledPane.getScene().getWidth() < width)
			{
				width = summaryTitledPane.getScene().getWidth();
			}
		}
		paneTitle.setPrefWidth(width - value);				
		paneTitle.setMaxWidth(width - value);
	}

	public void setStore(final ManagedMessageStoreWithFiltering store)
	{
		this.store = store;
	}
	
	public void setEventManager(final EventManager eventManager)
	{
		this.eventManager = eventManager;
	}

	/**
	 * 
	 * Sets the subscription tab for which this controller is.
	 * 
	 * @param tab The tab for which this controller is
	 */
	public void setTab(final Tab tab)
	{
		this.tab = tab;
	}

	public void setConnectionProperties(RuntimeConnectionProperties connectionProperties)
	{
		this.connectionProperties = connectionProperties;		
	}

	public Tab getTab()
	{
		return tab;
	}

	// public void setStatisticsManager(StatisticsManager statisticsManager)
	// {
	// this.statisticsManager = statisticsManager;
	// }
	
	public void setConnectionController(final ConnectionController connectionController)
	{
		this.connectionController = connectionController;
	}
	
	public void updateContextMenu()
	{
		if (subscription != null)
		{
			SubscriptionManager.updateSubscriptionTabContextMenu(tab, subscription);
		}
	}
	

	private Window getParentWindow()
	{
		return tab.getTabPane().getScene().getWindow();
	}
	
	public void showSearchWindow()
	{
		if (searchStage == null)
		{
			// Create the search window controller
			final FXMLLoader searchLoader = Utils.createFXMLLoader(this, Utils.FXML_LOCATION + "SearchWindow.fxml");
			AnchorPane searchWindow = Utils.loadAnchorPane(searchLoader);
			searchWindowController = (SearchWindowController) searchLoader.getController();
			searchWindowController.setStore(store);
			searchWindowController.setSubscription(subscription);
			searchWindowController.setSubscriptionName(subscription != null ? subscription.getTopic() : SubscriptionManager.ALL_SUBSCRIPTIONS_TAB_TITLE);
			searchWindowController.setEventManager(eventManager);
			
			eventManager.registerMessageAddedObserver(searchWindowController, store.getMessageList());
			eventManager.registerMessageRemovedObserver(searchWindowController, store.getMessageList());
					
			// Set scene width, height and style
			final Scene scene = new Scene(searchWindow, SearchWindowController.WIDTH, SearchWindowController.HEIGHT);
			scene.getStylesheets().addAll(tab.getTabPane().getScene().getStylesheets());
			
			searchStage = new Stage();
			searchStage.initModality(Modality.NONE);
			searchStage.initOwner(getParentWindow());
			searchStage.setScene(scene);
			
			searchWindowController.init();
		}

		if (!searchStage.isShowing())
		{
			searchStage.show();
			searchStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

				@Override
				public void handle(WindowEvent event)
				{
					searchButton.setSelected(false);
					searchWindowController.handleClose();
				}				
			});
		}		
		else
		{
			searchStage.close();
		}			
	}

	public void updateSubscriptionStats()
	{
		final int topicCount = store.getNonFilteredMessageList().getTopicSummary().getObservableMessagesPerTopic().size();
		final int filteredTopicCount = summaryTablePaneController.getFilteredDataSize();
		final int messageCount = store.getNonFilteredMessageList().getMessages().size(); 
		
		final String filteredTopics = topicCount != filteredTopicCount ? ("showing " + filteredTopicCount + "/") : "";
		final String topicCountText = filteredTopics + (topicCount == 1 ? "1 topic" : topicCount + " topics");
		final String messageCountText = messageCount == 1 ? "1 message" : messageCount + " messages";
		
		if (subscription == null)
		{
			statsLabel.setText(String.format(SUMMARY_PANE_STATS_FORMAT, 
				topicCountText,
				messageCountText,
				StatisticsManager.getMessagesReceived(connectionProperties.getId(), 5).overallCount,
				StatisticsManager.getMessagesReceived(connectionProperties.getId(), 30).overallCount,
				StatisticsManager.getMessagesReceived(connectionProperties.getId(), 300).overallCount));
		}
		else
		{
			final Double avg5sec = StatisticsManager.getMessagesReceived(connectionProperties.getId(), 5).messageCount.get(subscription.getTopic());
			final Double avg30sec = StatisticsManager.getMessagesReceived(connectionProperties.getId(), 30).messageCount.get(subscription.getTopic());
			final Double avg300sec = StatisticsManager.getMessagesReceived(connectionProperties.getId(), 300).messageCount.get(subscription.getTopic());
			
			statsLabel.setText(String.format(SUMMARY_PANE_STATS_FORMAT, 
					topicCountText,
					messageCountText,
					avg5sec == null ? 0 : avg5sec, 
					avg30sec == null ? 0 : avg30sec, 
					avg300sec == null ? 0 : avg300sec));
		}
	}
}
