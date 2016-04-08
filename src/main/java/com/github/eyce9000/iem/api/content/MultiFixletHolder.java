package com.github.eyce9000.iem.api.content;

import java.util.List;

import com.bigfix.schemas.bes.Fixlet;
import com.github.eyce9000.iem.api.content.ContentAPI.MultiContent;
import com.github.eyce9000.iem.api.content.impl.SingleFixletHolderImpl;
import com.github.eyce9000.iem.api.model.FixletID;

public interface MultiFixletHolder<F extends Fixlet> extends MultiContent<FixletID,F,SingleFixletHolder<F>>{

}