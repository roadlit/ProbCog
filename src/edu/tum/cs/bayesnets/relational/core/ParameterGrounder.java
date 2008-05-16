package edu.tum.cs.bayesnets.relational.core;

import java.util.Collection;
import java.util.Set;
import java.util.Vector;


public class ParameterGrounder {
	
	public static Collection<String[]> generateGroundings(RelationalNode node, Database db) throws Exception {
		return generateGroundings(db, node.getSignature().argTypes);
	}
	
	public static Collection<String[]> generateGroundings(Database db, String[] domainNames) throws Exception {
		Vector<String[]> ret = new Vector<String[]>();
		generateGroundings(ret, db, new String[domainNames.length], domainNames, 0);
		return ret;
	}
	
	private static void generateGroundings(Collection<String[]> ret, Database db, String[] params, String[] domainNames, int i) throws Exception {
		// if we have the full set of parameters, add it to the collection
		if(i == domainNames.length) {
			ret.add(params.clone());
			return;
		}
		// otherwise consider all ways of extending the current list of parameters using the domain elements that are applicable
		Set<String> domain = db.getDomain(domainNames[i]);		
		if(domain == null)
			throw new Exception("Domain " + domainNames[i] + " not found in the database!");
		for(String element : domain) {
			params[i] = element;
			generateGroundings(ret, db, params, domainNames, i+1);	
		}		
	}
}