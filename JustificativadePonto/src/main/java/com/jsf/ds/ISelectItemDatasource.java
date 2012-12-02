package com.jsf.ds;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

public interface ISelectItemDatasource extends Serializable {
    List<SelectItem> findObjects();
}
