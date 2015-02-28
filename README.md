#IEM4J#
#Java Library for IBM Endpoint Manager APIs#
______

##About the Library##
This library is a wrapper of the IBM Endpoint Manager REST API and Webreports API. This library does not cover the functionality of the REST API 100%, but is mostly focused on content (fixlet) manipulation.
______

##Build Instruction##
______
  git clone https://github.com/eyce9000/iem-client.git
  cd iem-client
  mvn package


##Using the Library##
____
###Modifying Content###

```java
  IEMClient client = new IEMClient("hostname","username","password");
  
  //Reading Fixlets
  Fixlet patchFixlet1 = client.getFixlet("Custom","Patching",1);
  
  //Reading Computer settings
  String settingValue = client.getComputerSetting(1,"SettingName");
  
  settingValue = "new setting value";
  //Updating Computer settings
  client.setComputerSetting(1,"SettingName",settingValue);
  
``` 
 
###Relevance Queries###

Relevance queries can be run using both the REST API and Webreports API. Both clients accept SessionRelevanceQuery objects, which wrap a normal session relevance query and provide support for named columns.

```java
  //Run Relevance Query through REST API
  SessionRelevanceQuery query = SessionRelevanceBuilder
      .fromRelevance("(name of it, id of it) of bes computers")
      .addColumns("computerName","computerId")
      .build();
   
  RelevanceClient client = new IEMClient(
      "hostname",
      "consoleUsername",
      "consolePassword");
  List<Map<String,Object>> restResults = client.executeQuery(query);
  
  //Run Relevance Query through Webreports API
  RelevanceClient relevanceClient = new WebreportsClient(
     "hostname",
     "webreportsUsername",
     "webreportsPassword");
     
  List<Map<String,Object>> webreportsResults = client.executeQuery(query);
``` 