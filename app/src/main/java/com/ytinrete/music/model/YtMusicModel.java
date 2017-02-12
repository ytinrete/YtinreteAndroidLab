package com.ytinrete.music.model;

import com.ytinrete.db.orm.OrmMusicFileFolder;
import com.ytinrete.db.orm.OrmMusicFileItem;
import com.ytinrete.dto.DtoMusicFileFolder;
import com.ytinrete.dto.DtoMusicFileItem;
import com.ytinrete.music.contracts.YtMusicContracts;
import com.ytinrete.mvp.model.BaseModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 *
 */

public class YtMusicModel extends BaseModel<YtMusicContracts.Model.Callback>
    implements YtMusicContracts.Model {

  private Realm realm = Realm.getDefaultInstance();


  @Override
  public void getLocalStoreMusicList() {
    RealmResults<OrmMusicFileFolder> fs = realm.where(OrmMusicFileFolder.class).findAll();
    if (fs.size() == 0) {
      getCallback().onLocalStoreMusicList(null);
    } else {
      ArrayList<DtoMusicFileFolder> folders = new ArrayList<>();
      for (OrmMusicFileFolder f : fs) {
        DtoMusicFileFolder newFolder = new DtoMusicFileFolder();
        newFolder.setFullPath(f.getFullPath());
        newFolder.setRelativePath(f.getRelativePath());
        newFolder.setFolderName(f.getFolderName());
        newFolder.setFiles(new ArrayList<DtoMusicFileItem>());
        for (OrmMusicFileItem i : f.getFiles()) {
          DtoMusicFileItem newFile = new DtoMusicFileItem();
          newFile.setFullPath(i.getFullPath());
          newFile.setRelativePath(i.getRelativePath());
          newFile.setFileName(i.getFineName());
          newFolder.getFiles().add(newFile);
        }
        folders.add(newFolder);
      }
      getCallback().onLocalStoreMusicList(folders);
    }
  }

  @Override
  public void initLocalStoreMusicList(ArrayList<DtoMusicFileFolder> res) {
    if (res != null) {
      realm.beginTransaction();
      for (DtoMusicFileFolder f : res) {
        OrmMusicFileFolder ormFolder = realm.createObject(OrmMusicFileFolder.class);
        ormFolder.setRelativePath(f.getRelativePath());
        ormFolder.setFolderName(f.getFolderName());
        ormFolder.setFullPath(f.getFullPath());
        for (DtoMusicFileItem i : f.getFiles()) {
          OrmMusicFileItem ormFile = realm.createObject(OrmMusicFileItem.class);
          ormFile.setFullPath(i.getFullPath());
          ormFile.setRelativePath(i.getRelativePath());
          ormFile.setFineName(i.getFileName());
          ormFolder.getFiles().add(ormFile);
        }
      }
      realm.commitTransaction();
    }
  }

  @Override
  public void clearAllLocalStoreMusicList() {
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<OrmMusicFileFolder> fs = realm.where(OrmMusicFileFolder.class).findAll();
        for (OrmMusicFileFolder f : fs) {
          f.getFiles().deleteAllFromRealm();
        }
        fs.deleteAllFromRealm();
      }
    });
  }

}
