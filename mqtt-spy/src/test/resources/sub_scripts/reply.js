function publishReply()
{
	if (!receivedMessage.getTopic().equals("/reply"))
	{
		mqttspy.publish(
			"/reply", "<simpleReply><topic>" + receivedMessage.getTopic() + "</topic>" 
			+ "<payload><![CDATA[" + receivedMessage.getMessage() + "]]></payload>"
			+ "</simpleReply>", 0, false);
		
		receivedMessage.setPayload("<tag>" + receivedMessage.getPayload() + "- modified :)</tag>");
	}
	else
	{
		receivedMessage.setPayload("<replyTag>" + receivedMessage.getPayload() + "- modified :)</replyTag>");
	}
	
	return true;
}

publishReply();
