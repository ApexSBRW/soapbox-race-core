#!/bin/bash
xjc -p com.soapboxrace.jaxb.http \
   Victory.TransferObjects.User.UserInfo.xsd \
   Victory.TransferObjects.DriverPersona.PersonaFriendsList.xsd \
   Victory.DataLayer.Serialization.User_Settings.xsd \
   Victory.DataLayer.Serialization.ClientConfigTrans.xsd \
   Victory.Service.SystemInfo.xsd \
   Victory.TransferObjects.Session.chatServer.xsd \
   Victory.Service.Objects.Event.CarClass.xsd \
   Victory.DataLayer.Serialization.FraudConfig.xsd \
   Victory.DataLayer.Serialization.UdpRelayInfo.xsd \
   Victory.DataLayer.Serialization.RegionInfo.xsd \
   Victory.DataLayer.Serialization.LoginAnnouncement.LoginAnnouncementsDefinition.xsd \
   Victory.DataLayer.Serialization.HeartBeat.xsd \
   Victory.DataLayer.Serialization.Event.EventDefinition.xsd \
   Victory.DataLayer.Serialization.CommerceResultTrans.xsd \
   Victory.DataLayer.Serialization.ProductTrans.xsd \
   Victory.EngineErrorCode.xsd \
   Victory.Service.EngineExceptionTrans.xsd

#Victory.DataLayer.Serialization.OwnedCarTrans.xsd 
   
cp -r com ../src/main/java
rm -rf com
