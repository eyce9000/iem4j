#IBM Endpoint Manager REST API and Webreports API for Java#
______

##Using the Library##
This library is a wrapper of the IBM Endpoint Manager REST API and Webreports API. It does not cover the functionality of the REST API 100%, but is mostly focused on content (fixlet) manipulation.

```java
  IEMClient client = new IEMClient("hostname","username","password");
  
  //Reading Fixlets
  Fixlet patchFixlet1 = client.getFixlet("Custom","Patching",1);
  
  //Reading Computer settings
  client.getComputerSetting(1,"SettingName");
  
  //Updating Computer settings
  client.setComputerSetting(1,"SettingName","SettingValue");
  
  //Run Relevance Query through REST API
  SessionRelevanceQuery query = SessionRelevanceBuilder.fromRelevance("(name of it, id of it) of bes computers")
    .addColumns("computerName","computerId")
    .build();
  List<Map<String,Object>> results = client.executeQuery(query);
  
  //Run Relevance Query through Webreports API
  RelevanceClient relevanceClient = new WebreportsClient("hostname","webreportsUsername","webreportsPassword");
  List<Map<String,Object>> webreportsResults = client.executeQuery(query);
``` 