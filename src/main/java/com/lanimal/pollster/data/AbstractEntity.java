package com.lanimal.pollster.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Version
    private long version;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated;

    @PreUpdate
    protected void automaticUpdateDate() {
        this.updated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return (new ReflectionToStringBuilderForEntities(AbstractEntity.this, ToStringStyle.SHORT_PREFIX_STYLE))
                .toString();
    }

    static class ReflectionToStringBuilderForEntities extends ReflectionToStringBuilder {

        ReflectionToStringBuilderForEntities(Object object, ToStringStyle style) {
            super(object, style);
        }

        @Override
        protected boolean accept(Field f) {
            return super.accept(f) && !f.getType()
                    .isAnnotationPresent(Entity.class);
        }
    }
}
