package net.opentracker.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 * https://jrtechs.net/java/gremlin-in-10-minutes
 */
public class App {
	
	public void add(String name, float score) {
		RemoteConnection con = new RemoteConnection();
		String query = "g.addV(\"student\").property(\"name\", "+name+").property(\"GPA\","+score+")";
		con.queryGraph(query);
	}
	
	private List<Player> getFriendsFromGraph(String id) {
		RemoteConnection con = new RemoteConnection();
		
	    List<Player> friends = new ArrayList<>();
	    String query = "g.V().hasLabel('player')" +
	            ".has('id', '" + id + "')" +
	            ".both().valueMap()";
	    con.queryGraph(query).stream().forEach(r ->
	            friends.add(new Player(
	                    ((ArrayList) (((HashMap<String, Object>) (r.getObject()))
	                            .get("name"))).get(0).toString(),
	                    ((ArrayList) (((HashMap<String, Object>) (r.getObject()))
	                            .get("id"))).get(0).toString()))
	    );
	    return friends;
	}
	
    public static void main( String[] args )
    {
    	String p1= "1234";
    	String p2= "5678";
    	
    	RemoteConnection con = new RemoteConnection();
    	String query = "g.V().hasLabel('player')" +
        		        ".has('id', '" + p1 + "')" +
        		        ".as('p1')" +
        		        "V().hasLabel('player')" +
        		        ".has('id', '" + p2 + "')" +
        		        ".as('p2')" +
        		        ".addE('friends')" +
        		        ".from('p1').to('p2')";
        		con.queryGraph(query);
    }
}
