package com.spring.jwt.SavePart;

import java.util.List;

public interface SavePartService {
    String savePart (SavePartDto savePartDto);
    public SavePart getSavedPartById(Integer savePartId);

    public List<SavePartDto> getSavedPart(int userId);

    public String deleteSavedParById(Integer savePartId);

    public SavePart getByPartAndUserId(int userId, Integer sparePartId);
}
