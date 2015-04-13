#xjc -d src/main/java/ -p com.bigfix.schemas.besapi -b schema/9.2/binding.xml schema/9.2/BESAPI.xsd 
xjc -extension -d src/main/java/ -p com.bigfix.schemas.bes -b schema/9.2/binding.xml schema/9.2/BES.xsd 
