package treningsapp.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import treningsapp.json.TreningsappModule;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TreningModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private ObjectMapper objectMapper;

    public TreningModuleObjectMapperProvider() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new TreningsappModule());
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        //System.out.println("Bruker getContext:" + type);
        return objectMapper;
    }

}
