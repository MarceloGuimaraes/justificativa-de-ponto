package com.managed.bean;

import com.domain.dto.JustificativaPontoGrid;
import com.service.IConsultaPaginadaService;

public class JustificativaPaginadaDatasource extends MinhasJustificativasPaginadasDatasource {

    public JustificativaPaginadaDatasource(IPermissoesBean permissoes,
                                           IConsultaPaginadaService<JustificativaPontoGrid> serviceAdmin,
                                           IConsultaPaginadaService<JustificativaPontoGrid> serviceUser) {
        super(serviceUser);
        if(permissoes.isAdmin() || permissoes.isSupport() || permissoes.isRh()){
            this.service = serviceAdmin;
        }
    }

}
