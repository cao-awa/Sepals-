package com.github.cao.awa.sepals.transform.mixin.config;

import com.alibaba.fastjson2.JSONObject;
import com.github.cao.awa.apricot.util.collection.ApricotCollectionFactor;
import com.github.cao.awa.sepals.transform.mixin.config.handler.SepalsMixinHandlerConfig;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class SepalsMixinConfig {
    private final Map<String, SepalsMixinHandlerConfig> handlerConfigs = ApricotCollectionFactor.hashMap();

    public static SepalsMixinConfig create(JSONObject json) {
        SepalsMixinConfig config = new SepalsMixinConfig();
        String packageAt = json.getString("package");

        Optional.ofNullable(json.getJSONObject("handlers")).ifPresent(configs -> {
            for (String key : configs.keySet()) {
                JSONObject mixinHandlerConfig = configs.getJSONObject(key);
                String fullName = packageAt + "." + key;
                config.handlerConfigs.put(fullName, SepalsMixinHandlerConfig.create(mixinHandlerConfig, fullName));
            }
        });
        return config;
    }

    public SepalsMixinHandlerConfig getHandlerConfig(String mixinClassName) {
        return this.handlerConfigs.get(mixinClassName);
    }

    public <X> X ifHasHandlerConfig(String mixinClassName, Function<SepalsMixinHandlerConfig, X> consumer, Supplier<X> defaultValue) {
        SepalsMixinHandlerConfig config = getHandlerConfig(mixinClassName);
        if (config != null && config.handler() != null) {
            return consumer.apply(config);
        }
        return defaultValue.get();
    }
}
