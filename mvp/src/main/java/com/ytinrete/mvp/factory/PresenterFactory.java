package com.ytinrete.mvp.factory;


import com.ytinrete.mvp.presenter.Presenter;

public interface PresenterFactory<P extends Presenter> {
  P createPresenter();
}
