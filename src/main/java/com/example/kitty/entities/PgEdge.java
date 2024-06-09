package com.example.kitty.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PgEdge {
    public Long gid;
    public Integer tagId;
    public Long source;
    public Long target;
    public Double length;
    public LatLong start;
    public LatLong end;

    public PgEdge(Object[] data, Integer offset) {
        this.gid = (Long) data[offset];
        this.tagId = (Integer) data[offset + 1];
        this.source = (Long) data[offset + 2];
        this.target = (Long) data[offset + 3];
        this.length = (Double) data[offset + 4];
        this.start = new LatLong((Double) data[offset + 6], (Double) data[offset + 5]);
        this.end = new LatLong((Double) data[offset + 8], (Double) data[offset + 7]);
    }

    public void swapEdge() {
        var prevStartCopy = start;
        this.start = this.end;
        this.end = prevStartCopy;

        var prevSourceCopy = source;
        this.source = this.target;
        this.target = prevSourceCopy;
    }
}
