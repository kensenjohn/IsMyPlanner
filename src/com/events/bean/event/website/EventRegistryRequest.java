package com.events.bean.event.website;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventRegistryRequest {
    private String eventRegistryId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String url = Constants.EMPTY;
    private String instructions = Constants.EMPTY;

    public String getEventRegistryId() {
        return eventRegistryId;
    }

    public void setEventRegistryId(String eventRegistryId) {
        this.eventRegistryId = eventRegistryId;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventRegistryRequest{");
        sb.append("eventRegistryId='").append(eventRegistryId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", instructions='").append(instructions).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
