package com.artist.investment.provider;

import com.artist.investment.constant.ProjectDurationUnit;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2018-01-19 09:36:32.
 */
@Component
public class ProjectDurationUnitProvider implements ValueProvider {
    private static final List<ValuePair<?>> buffer;

    static {
        buffer = new ArrayList<ValuePair<?>>();
        buffer.add(new ValuePairImpl(ValueProvider.EMPTY_ITEM_TEXT, null));
        for(ProjectDurationUnit projectDurationUnit : ProjectDurationUnit.values()){
            buffer.add(new ValuePairImpl(projectDurationUnit.getText(), projectDurationUnit.getCode()));
        }
    }

    @Override
    public List<ValuePair<?>> getLookupList(Object obj, Map metaMap, FieldMeta fieldMeta) {
        return buffer;
    }

    @Override
    public String getDisplayText(Object obj, Map metaMap, FieldMeta fieldMeta) {
        if(obj == null || obj.equals("")) return null;
        for(ValuePair<?> valuePair : buffer){
            if(obj.toString().equals(valuePair.getValue())){
                return valuePair.getText();
            }
        }
        return null;
    }
}