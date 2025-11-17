package com.gary.mqtthttpbridge.service.impl;

import com.gary.mqtthttpbridge.mapper.PackMapper;
import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.model.RestPackAll;
import com.gary.mqtthttpbridge.service.PackService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 */
@Service
public class ImplPackService implements PackService {
    @Resource
    private PackMapper packMapper;

    @Override
    public RestPackAll getPackAll() {
        RestPackAll restPackAll = new RestPackAll();
        List<Pack> packList = new ArrayList<>();
        packList = packMapper.selectPackAll();
        // 3. 遍历查询结果，将每个pack的数据设置到返回对象中
        for (Pack pack : packList) {
            String packId = pack.getPackId();
            switch (packId) {
                case "pack1":
                    restPackAll.setPack1(pack);
                    break;
                case "pack2":
                    restPackAll.setPack2(pack);
                    break;
                case "pack3":
                    restPackAll.setPack3(pack);
                    break;
                case "pack4":
                    restPackAll.setPack4(pack);
                    break;
                case "pack5":
                    restPackAll.setPack5(pack);
                    break;
                case "pack6":
                    restPackAll.setPack6(pack);
                    break;
                case "pack7":
                    restPackAll.setPack7(pack);
                    break;
                case "pack8":
                    restPackAll.setPack8(pack);
                    break;
                default:
                    // 忽略非目标pack的数据（如存在其他pack_id）
                    break;
            }
        }
        return restPackAll;
    }

    @Override
    public Object getLastPack() {
        Pack newPack = packMapper.selectOneByTime();
        System.out.println("ImplPackService.getLastPack.newPack ---> " + newPack.toString());
        return packMapper.selectOneByTime();
    }

    @Override
    public Integer postOnePack(Pack newPack) {
        return packMapper.insert(newPack);
    }
}
