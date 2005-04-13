/*
 * Created on 2005-04-09
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.flexdock.view.restore;

import org.flexdock.view.View;

/**
 * 
 * @author Mateusz Szczap
 */
public interface IViewRestorationManager {

	void registerTerritoralView(View view);
	
	void registerView(String viewId, ViewDockingInfo mainViewDockingInfo);

	void unregisterView(String viewId);

	boolean showView(View view);

	boolean hideView(View view); 
	
	void setPreservingStrategy(PreservingStrategy preservingStrategy);

	PreservingStrategy getPreservingStrategy();

}