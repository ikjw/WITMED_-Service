package com.example.test.old.entity.datapointBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class sleepDataPoint extends dataPoint {
    protected int type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof sleepDataPoint)) return false;

        sleepDataPoint sleepDataPoint = (sleepDataPoint) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getType(), sleepDataPoint.getType()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getType()).toHashCode();
    }
}
