package me.koeb.ResPlan;

/**
 * Hello world!
 *
 */
import org.skife.jdbi.v2.DBI;

import me.koeb.ResPlan.dao.CustomerDAO;
import me.koeb.ResPlan.health.TemplateHealthCheck;
import me.koeb.ResPlan.resources.CustomerResource;
import me.koeb.ResPlan.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.db.DataSourceFactory;

//import me.koeb.ResPlan.resources.ResPlanResource;
//import me.koeb.ResPlan.health.TemplateHealthCheck;

public class ResPlanApplication extends Application<ResPlanConfiguration>
{
    public static void main( String[] args ) throws Exception
    {
    	new ResPlanApplication().run(args);
    }
    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ResPlanConfiguration> bootstrap) {
    	bootstrap.addBundle(new MigrationsBundle<ResPlanConfiguration>() {
            @Override
                public DataSourceFactory getDataSourceFactory(ResPlanConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
        });
    	//bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
        //bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
        //bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
        //bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "fonts"));
        

    }

    @Override
    public void run(ResPlanConfiguration configuration, Environment environment) throws ClassNotFoundException
    {
        // nothing to do yet
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
            );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
            environment.healthChecks().register("template", healthCheck);
            environment.jersey().register(resource);
            
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
        final CustomerDAO dao = jdbi.onDemand(CustomerDAO.class);
        environment.jersey().register(new CustomerResource(dao));
    }

}
