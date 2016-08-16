package com.project.estevao.csvproject.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Estevao on 15/08/2016.
 */
public class YourEntity {
    private String name;
    private Long id;
    private String text;

    public YourEntity(Long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public YourEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("text", getText());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }
}
