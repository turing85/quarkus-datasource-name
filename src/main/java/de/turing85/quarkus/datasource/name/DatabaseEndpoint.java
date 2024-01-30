package de.turing85.quarkus.datasource.name;

import java.lang.annotation.Annotation;

import javax.sql.DataSource;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path(DatabaseEndpoint.PATH)
public class DatabaseEndpoint {
  public static final String PATH = "datasource";

  @GET
  public Response getDatasource(@QueryParam("name") String name) {
    Instance<DataSource> instance = getDbInstanceByName(name);
    String text = "I have %s datasource with name \"%s\""
        .formatted(instance.isResolvable() ? "a" : "no", name);
    Response.Status status =
        instance.isResolvable() ? Response.Status.OK : Response.Status.NOT_FOUND;
    return Response.status(status).entity(text).build();
  }

  private static Instance<DataSource> getDbInstanceByName(String name) {
    return CDI.current().select(DataSource.class, constructNamedAnnotationInstance(name));
  }

  private static Named constructNamedAnnotationInstance(String name) {
    return new Named() {
      @Override
      public String value() {
        return name;
      }

      @Override
      public Class<? extends Annotation> annotationType() {
        return Named.class;
      }
    };
  }
}
