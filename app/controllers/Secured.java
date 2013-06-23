package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

public class Secured extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("id");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        ObjectNode result = Json.newObject();
		result.put("message", "Unauthorized user, please login");
		return unauthorized(result);
    }

}