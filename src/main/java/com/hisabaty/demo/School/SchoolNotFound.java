




public class SchoolNotFound extends RuntimeException
{

    public SchoolNotFound(String name)
    {
        super("School not found with name : " + name);
    }
    
    public static ResponseEntity<String name>  WebPage(String name)
    {
        return ResponseEntity.HttpStatus.NOT_FOUND.body(name);
    }
}