package com.ismos_salt_erp.view.fragment.production.packaging;

import com.ismos_salt_erp.localDatabase.PackagingDatabaseModel;

public interface PackagingRecyclerItemClickHandle {
    void selectItemName(int position, int adapterPosition, PackagingDatabaseModel model);

    void setPreviousSelectedItemName(int adapterPosition, String previousItemNameId, String currentSelectedPackedId);

    void selectPackedName(int position, int adapterPosition, PackagingDatabaseModel model);
    /**
     * For Handle realtime quantity change
     */
    void changeQuantity(String currentQuantity,int adapterPosition, PackagingDatabaseModel packagingDatabaseModel);
    /**
     * For Handle realtime note
     */
    void changeNote(String currentNote,String currentQuantity,int adapterPosition, PackagingDatabaseModel packagingDatabaseModel);

    void removeItem(int position);
}
