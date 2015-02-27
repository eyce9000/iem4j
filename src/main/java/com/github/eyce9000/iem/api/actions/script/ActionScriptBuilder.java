package com.github.eyce9000.iem.api.actions.script;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.bigfix.schemas.bes.ActionScript;
import com.bigfix.schemas.besapi.BESAPI.ComputerSettings;
import com.bigfix.schemas.besapi.ComputerSetting;

public class ActionScriptBuilder {
	List<String> actionScriptLines = new LinkedList<String>();
	public ActionScript build(){
		return build(null);
	}
	public ActionScript build(String mimeType){

		ActionScript script = new ActionScript();
		StringBuilder builder = new StringBuilder();
		for(String line:actionScriptLines){
			builder.append(line + "\n");
		}
		script.setValue(builder.toString());
		script.setMIMEType(mimeType);
		return script;
	}
	
	public ActionScriptBuilder comment(String comment){
		for(String line : comment.split("\n"))
			line("//"+line);
		return this;
	}
	
	public ActionScriptBuilder line(){
		actionScriptLines.add("");
		return this;
	}
	
	/**
	 * Adds the <code>scriptLine</code> as a line in the script. 
	 * The line is expected to be a single line, so all newlines ('\n') contained within it are replaced with blank spaces.
	 * @param scriptLine
	 * @return
	 */
	public ActionScriptBuilder line(String scriptLine){
		scriptLine.replace("\n", " ");
		actionScriptLines.add(scriptLine);
		return this;
	}
	
	/**
	 * Adds <code>count</code> number of empty lines to the script
	 * @param count
	 * @return
	 */
	public ActionScriptBuilder lines(int count){
		for(int i=0; i<count; i++)
			actionScriptLines.add("");
		return this;
	}
	
	/**
	 * Adds <code>lines</code> to the script. 
	 * Each line is expected to be a single line, so all newlines ('\n') contained within it are replaced with blank spaces.
	 * @param lines
	 * @return
	 */
	public ActionScriptBuilder lines(Collection<String> lines){
		for(String line:lines)
			line(line);
		return this;
	}

	/**
	 * Adds <code>lines</code> to the script. 
	 * Each line is expected to be a single line, so all newlines ('\n') contained within it are replaced with blank spaces.
	 * @param lines
	 * @return
	 */
	public ActionScriptBuilder lines(String... lines){
		return lines(Arrays.asList(lines));
	}
	
	public ActionScriptBuilder lines(String prefix, String... lines){
		return lines(prefix,Arrays.asList(lines));
	}
	
	public ActionScriptBuilder lines(String prefix, Collection<String> lines){
		for(String line:lines)
			line(prefix+line);
		return this;
	}
	
	/**
	 * Sets the computer setting <code>key</code> to <code>value</code> for the client site at client time <code>{now}</code> 
	 * @param key
	 * @param value
	 * @return
	 */
	public ActionScriptBuilder putComputerSettingNow(String key, String value){
		return line(String.format("setting \"%s\"=\"%s\" on \"{now}\" for client",key,value));
	}

	/**
	 * Sets the computer <code>setting</code>  for the client site at client time <code>{now}</code> 
	 * @param key
	 * @param value
	 * @return
	 */
	public ActionScriptBuilder putComputerSettingNow(ComputerSetting setting){
		return putComputerSettingNow(setting.getName(),setting.getValue());
	}

	/**
	 * Sets a the computer <code>settings</code>  for the client site at client time <code>{now}</code> 
	 * @param key
	 * @param value
	 * @return
	 */
	public ActionScriptBuilder putComputerSettingNow(ComputerSettings settings){
		for(ComputerSetting setting:settings.getSetting()){
			putComputerSettingNow(setting);
		}
		return this;
	}

	/**
	 * Deletes the computer setting <code>key</code>  for the client site at client time <code>{now}</code> 
	 * @param key
	 * @param value
	 * @return
	 */
	public ActionScriptBuilder deleteComputerSettingNow(String key){
		return line(String.format("setting delete \"%s\" on \"{now}\" for client",key));
	}
	
	/**
	 * Deletes the computer settings <code>keys</code>  for the client site at client time <code>{now}</code> 
	 * @param keys
	 * @param value
	 * @return
	 */
	public ActionScriptBuilder deleteComputerSettingsNow(String... keys) {
		for(String setting:keys){
			deleteComputerSettingNow(setting);
		}
		return this;
	}
	
	/**
	 * Creates file <code>filepath</code> with the contents <code>filecontents</code>. 
	 * This method uses [EOF] as the default delimeter when creating the file.
	 * @param filecontents the raw contents of the file. May include newlines.
	 * @param filepath
	 * @return
	 */
	
	public ActionScriptBuilder createFile(String filecontents, String filepath){
		return createFile(filecontents,filepath,true);
	}
	
	/**
	 * Creates file <code>filepath</code> with the contents <code>filecontents</code>. 
	 * This method uses [EOF] as the default delimeter when creating the file.
	 * 
	 * @param filecontents the raw contents of the file. May include newlines.
	 * @param filepath the path of the file to be created
	 * @param overwrite overwrite any file that may already exist at <code>filepath</code>
	 * @return
	 */
	public ActionScriptBuilder createFile(String filecontents, String filepath,boolean overwrite){
		return createFile(filecontents,filepath,true,"[EOF]");
	}

	/**
	 * Creates file <code>filepath</code> with the contents <code>filecontents</code>. 
	 * <code>delimeter</code> is used to indicate within the script where the file starts and stops.
	 * @param filecontents the raw contents of the file. May include newlines.
	 * @param filepath the path of the file to be created
	 * @param overwrite overwrite any file that may already exist at <code>filepath</code>
	 * @param delimeter indicates within the actionscript where the file starts and stops
	 * @return
	 */
	public ActionScriptBuilder createFile(String filecontents,String filepath,boolean overwrite,String delimeter){
		line("createfile until "+delimeter);
		lines(Arrays.asList(filecontents.split("\n")));
		line(delimeter);
		move("__createfile",filepath,overwrite);
		return this;
	}
	
	public ActionScriptBuilder wait(String target){
		return line("wait \""+target+"\"");
	}
	
	public ActionScriptBuilder move(String source,String target,boolean overwrite){
		if(overwrite)
			delete(target);
		actionScriptLines.add("move \""+source+"\" \""+target+"\"");
		return this;
	}
	
	public ActionScriptBuilder delete(String target){
		return line("delete \""+target+"\"");
	}
	
	public ActionScriptBuilder conditional(String statement, ActionScriptBuilder scriptBlock){
		line("if "+statement);
		lines("\t",scriptBlock.actionScriptLines);
		line("endif");
		return this;
	}
	
	public ActionScriptBuilder prefetch(String filename,String url, String sha1, long size){
		return line("prefetch "+filename+" sha1:"+sha1+" size:"+size+" "+url);
	}
	
	public ActionScriptBuilder script(ActionScript script){
		return lines(script.getValue().split("\n"));
	}
	
	public static ActionScriptBuilder start(){
		ActionScriptBuilder builder = new ActionScriptBuilder();
		return builder;
	}
}
