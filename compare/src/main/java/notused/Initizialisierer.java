package notused;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import at.compare.init.WebAppConfig;

public class Initizialisierer implements WebApplicationInitializer{
	
	
	 public void onStartup(ServletContext container) {
	      // Create the 'root' Spring application context
	      AnnotationConfigWebApplicationContext rootContext =
	        new AnnotationConfigWebApplicationContext();
	      rootContext.register(WebAppConfig.class);

	      // Manage the lifecycle of the root application context
	      //container.addListener(new ContextLoaderListener(rootContext));
	      
	      rootContext.setServletContext(container);
	      
	      // Create the dispatcher servlet's Spring application context
	      /*
	      AnnotationConfigWebApplicationContext dispatcherContext =
	        new AnnotationConfigWebApplicationContext();
	      dispatcherContext.register(LinkController.class);
	       */
	      // Register and map the dispatcher servlet
	      ServletRegistration.Dynamic dispatcher =
	        container.addServlet("dispatcher", new DispatcherServlet(rootContext));
	      dispatcher.setLoadOnStartup(1);
	      dispatcher.addMapping("/");
	    }

}
