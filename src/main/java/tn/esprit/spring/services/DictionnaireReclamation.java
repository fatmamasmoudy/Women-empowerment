package tn.esprit.spring.services;

import java.util.Hashtable;
import java.util.Map;

public class DictionnaireReclamation {
private static Map<String,String> decisions;

public DictionnaireReclamation(){
	
}

public static Map<String, String> getDecisions() {
	decisions=new Hashtable<>();
	decisions.put("Ba", "Bah"); 
	decisions.put("Aa", "aha"); 
	decisions.put("Bt", "Braa"); 
	decisions.put("Da", "war"); 
	decisions.put("aK", "raj"); 
	decisions.put("Re", "aha"); 
	
	return decisions;
}






}
