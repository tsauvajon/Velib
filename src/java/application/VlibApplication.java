/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import carto.Carto;
import carto.Carto.Markers.Marker;
import java.io.Serializable;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlDataTable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import station.Station;

/**
 *
 * @author guillaume.baechler
 */
// @Named(value = "vlibapplication")
// @Dependent
@ManagedBean
@ApplicationScoped
public class VlibApplication implements Serializable {

    private static final long serialVersionUID = 7983140976075649622L;
    private Carto mycarto;
    private HtmlDataTable datatableMarkers;
    private Station mySelectedStation;
    private Marker mySelectedMarker;
    private String key = "???";
    private String link = "https://maps.googleapis.com/maps/api/js?key=" + key + "&sensor=TRUE&callback=initMap";
    
    @PostConstruct
    void init() {
        try {
            JAXBContext jc = JAXBContext.newInstance("carto");
            Unmarshaller u = jc.createUnmarshaller();
            Marshaller m = jc.createMarshaller();
            Carto newCarto = (Carto) u.unmarshal(new URL("http://www.velib.paris/service/carto").openStream());
            setMycarto(newCarto);
        } catch (Exception ex) {
            Logger.getLogger(VlibApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new instance of ApplicationCounter
     */
    public VlibApplication() {
    }

    public String show(String n) {
        Marker marker = (Marker) datatableMarkers.getRowData();
        try {
            JAXBContext jc = JAXBContext.newInstance("station");
            Unmarshaller u = jc.createUnmarshaller();
            Marshaller m = jc.createMarshaller();
            Station myStation = (Station) u.unmarshal(new URL("http://www.velib.paris/service/stationdetails/"+marker.getNumber()).openStream());
            setmySelectedStation(myStation);
            setMySelectedMarker(marker);
        } catch (Exception ex) {
            Logger.getLogger(VlibApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "station";
    }

    /**
     * @return the mycarto
     */
    public Carto getMycarto() {
        return mycarto;
    }

    /**
     * @param mycarto the mycarto to set
     */
    public void setMycarto(Carto mycarto) {
        this.mycarto = mycarto;
    }

    public HtmlDataTable getDatatableMarkers() {
        return datatableMarkers;
    }

    public void setDatatableMarkers(HtmlDataTable datatableMarkers) {
        this.datatableMarkers = datatableMarkers;
    }

    /**
     * @return the mySelectedMarker
     */
    public Station getmySelectedStation() {
        return mySelectedStation;
    }

    /**
     * @param mySelectedMarker the mySelectedMarker to set
     */
    public void setmySelectedStation(Station mySelectedStation) {
        this.mySelectedStation = mySelectedStation;
    }

    /**
     * @return the mySelectedMarker
     */
    public Marker getMySelectedMarker() {
        return mySelectedMarker;
    }

    /**
     * @param mySelectedMarker the mySelectedMarker to set
     */
    public void setMySelectedMarker(Marker mySelectedMarker) {
        this.mySelectedMarker = mySelectedMarker;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

}
