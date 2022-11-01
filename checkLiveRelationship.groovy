/* Query to list all paths which has specific component authored - Result will be a direct component path node under "/content" */
def queryManager = session.workspace.queryManager;
def sqlQuery = "select [jcr:path], * from [nt:unstructured] as a where [sling:resourceType] = 'weretail/components/structure/page' and isdescendantnode(a, '/content/we-retail')";
def queryObj = queryManager.createQuery(sqlQuery, 'sql');
def queryResults = queryObj.execute();
def renamedCt = 0;
def properties = "";

queryResults.nodes.each {
    node ->
        String nodePath = node.path;
        
        if (node.hasNode('cq:LiveSyncConfig')) {
           // println nodePath + " is a LiveCopy";
            if (node.hasProperty('jcr:mixinTypes')) {
                //println "Path:: " + nodePath;
                node.getProperty('jcr:mixinTypes').each {
                    property -> 
                        String[] values = property.getValues();
                        
                        if (!values.contains("cq:LiveRelationship")) {
                            println "Path:: " + nodePath + " is a LiveCopy but is missing the cq:LiveRelationship mixin";    
                        }
                }
            }
        } 
        
        
}
if(renamedCt == 0){
    println "No node needs update !!"
}
