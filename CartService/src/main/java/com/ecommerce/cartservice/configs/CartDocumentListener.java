package com.ecommerce.cartservice.configs;

import com.ecommerce.cartservice.models.BaseModel;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CartDocumentListener extends AbstractMongoEventListener<BaseModel> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseModel> event) {
        BaseModel doc = event.getSource();
        if (doc.getCreatedAt() == null) {
            doc.setCreatedAt(Instant.now());
        }
        doc.setUpdatedAt(Instant.now());
    }
}
