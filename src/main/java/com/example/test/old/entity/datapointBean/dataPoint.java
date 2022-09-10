package com.example.test.old.entity.datapointBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.time.LocalDateTime;

/**
 * 所有采样数据的基类
 * 其等价性判断不包括id字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class dataPoint {
    protected long id;
    protected String username;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime startTime;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        dataPoint dataPoint = (dataPoint) o;

        return new EqualsBuilder().append(username, dataPoint.username).append(startTime, dataPoint.startTime).append(endTime, dataPoint.endTime).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).append(startTime).append(endTime).toHashCode();
    }
}
