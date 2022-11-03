package org.vaadin.example;

import com.vaadin.flow.component.Key;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarScheduler;
import org.vaadin.stefan.fullcalendar.dataprovider.EntryProvider;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.http11.HeadersTooLargeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.FullCalendar;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

	private FullCalendar calendar;

    public MainView(@Autowired GreetService service) {

    	calendar = new FullCalendar();
    	
    	calendar.setSizeFull();

    	calendar.setHeightByParent();
    	
    	calendar.setEntryDidMountCallback("" +
    		    "function (info) {" +
    		    "    console.log('---------------------> setEntryDidMountCallback');" +
    		    "    console.log(info);" +
    		    "}");
    	
		calendar.setEntryContentCallback("" +
		    "function (info) {" +
		    "    console.log('---------------------> setEntryContentCallback');" +
		    "    console.log(info);" +
		    "    info.backgroundColor = info.event.getCustomProperty('selected', false) ? 'lightblue' : 'lightgreen';" +
		    "}");
		
		List<Entry> entries = new ArrayList<Entry>();
		
		Entry e1 = new Entry();
		e1.setTitle("Test event 1");
		e1.setStart(LocalDate.now());
		e1.setEnd(e1.getStart().plusDays(1));
		e1.setAllDay(true);
		entries.add(e1);
		
		Entry e2 = new Entry();
		e2.setTitle("Test event 2");
		e2.setStart(LocalDate.now().atStartOfDay().plusHours(10));
		e2.setEnd(e2.getStart().plusHours(2));
		entries.add(e2);
		
		calendar.setEntryProvider(EntryProvider.lazyInMemoryFromItems(entries));
    	
		setSizeFull();
		
		add(calendar);
    }

}
