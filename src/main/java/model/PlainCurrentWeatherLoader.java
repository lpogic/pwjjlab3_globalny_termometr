package model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Stack;

public class PlainCurrentWeatherLoader extends DefaultHandler {
    private static final String urlPart1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String urlPart2 = "&mode=xml&apikey=ab3a3a4743c325386addd4cfe19adfba";

    public static PlainCurrentWeather load(String q) {
        try {
            PlainCurrentWeather plainCurrentWeather = new PlainCurrentWeather();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            System.out.println(urlPart1 + q + urlPart2);
            parser.parse(urlPart1 + q + urlPart2, new PlainCurrentWeatherLoader(plainCurrentWeather));
            return plainCurrentWeather;
        } catch (Exception e) {
            return null;
        }
    }

    private Stack<String> elements;
    private PlainCurrentWeather plainCurrentWeather;

    public PlainCurrentWeatherLoader(PlainCurrentWeather plainCurrentWeather) {
        this.plainCurrentWeather = plainCurrentWeather;
        this.elements = new Stack<>();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (elements.peek().equals("country")) {
            plainCurrentWeather.setCountry(new String(ch, start, length));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        elements.pop();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        elements.push(qName);
        switch(qName){
            case "city":
                plainCurrentWeather.setCity(attributes.getValue("name"));
                break;
            case "country":
                plainCurrentWeather.setCountry(attributes.getValue("name"));
                break;
            case "temperature":
                plainCurrentWeather.setTemperature(Double.parseDouble(attributes.getValue("value")) - 272.15);
                break;
            case "humidity":
                plainCurrentWeather.setHumidity(Integer.parseInt(attributes.getValue("value")));
                break;
            case "pressure":
                plainCurrentWeather.setPressure(Integer.parseInt(attributes.getValue("value")));
                break;
            case "speed":
                plainCurrentWeather.setWindSpeed(Double.parseDouble(attributes.getValue("value")));
                break;
            case "direction":
                plainCurrentWeather.setWindDirection(attributes.getValue("name"));
                break;
            case "visibility":
                plainCurrentWeather.setVisibility(Integer.parseInt(attributes.getValue("value")));
                break;
            case "weather":
                plainCurrentWeather.setWeatherName(attributes.getValue("value"));
                break;
        }

    }
}
