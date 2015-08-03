/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.guvnor.m2repo.client.widgets;

import java.util.Date;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.HasData;
import org.guvnor.m2repo.client.editor.JarDetailPopup;
import org.guvnor.m2repo.client.resources.i18n.M2RepoEditorConstants;
import org.guvnor.m2repo.model.JarListPageRequest;
import org.guvnor.m2repo.model.JarListPageRow;
import org.guvnor.m2repo.service.M2RepoService;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.ext.widgets.common.client.tables.PagedTable;

@Dependent
public class ArtifactListViewImpl extends Composite implements ArtifactListView {

    protected final PagedTable<JarListPageRow> dataGrid = new PagedTable<JarListPageRow>();

    protected ArtifactListPresenter presenter;

    @Inject
    protected Caller<M2RepoService> m2RepoService;

    @Inject
    private ErrorPopupPresenter errorPopup;

    protected String currentFilter = "";

    public ArtifactListViewImpl() {
        dataGrid.setEmptyTableCaption( M2RepoEditorConstants.INSTANCE.NoArtifactAvailable() );

        final Column<JarListPageRow, String> nameColumn = new Column<JarListPageRow, String>( new TextCell() ) {
            @Override
            public String getValue( JarListPageRow row ) {
                return row.getName();
            }
        };
        nameColumn.setSortable( true );
        nameColumn.setDataStoreName( JarListPageRequest.COLUMN_NAME );
        dataGrid.addColumn( nameColumn,
                            M2RepoEditorConstants.INSTANCE.Name() );

        final Column<JarListPageRow, String> pathColumn = new Column<JarListPageRow, String>( new TextCell() ) {
            @Override
            public String getValue( JarListPageRow row ) {
                return row.getPath();
            }
        };
        pathColumn.setSortable( true );
        pathColumn.setDataStoreName( JarListPageRequest.COLUMN_PATH );
        dataGrid.addColumn( pathColumn,
                            M2RepoEditorConstants.INSTANCE.Path() );

        final Column<JarListPageRow, Date> lastModifiedColumn = new Column<JarListPageRow, Date>( new DateCell( DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM ) ) ) {
            @Override
            public Date getValue( JarListPageRow row ) {
                return row.getLastModified();
            }
        };
        lastModifiedColumn.setSortable( true );
        lastModifiedColumn.setDataStoreName( JarListPageRequest.COLUMN_LAST_MODIFIED );
        dataGrid.addColumn( lastModifiedColumn,
                            M2RepoEditorConstants.INSTANCE.LastModified() );

        // Add "View kjar detail" button column
        final Column<JarListPageRow, String> openColumn = new Column<JarListPageRow, String>( new ButtonCell( ButtonSize.EXTRA_SMALL ) ) {
            public String getValue( JarListPageRow row ) {
                return M2RepoEditorConstants.INSTANCE.Open();
            }
        };
        openColumn.setFieldUpdater( new FieldUpdater<JarListPageRow, String>() {
            public void update( int index,
                                JarListPageRow row,
                                String value ) {
                m2RepoService.call( new RemoteCallback<String>() {
                    @Override
                    public void callback( final String response ) {
                        JarDetailPopup popup = new JarDetailPopup( response );
                        popup.show();
                    }
                }, new ErrorCallback<Message>() {
                    @Override
                    public boolean error( Message message, Throwable throwable ) {
                        errorPopup.showMessage( "Unexpected error encountered: " + throwable.getMessage() );
                        return true;
                    }
                } ).getPomText( row.getPath() );
            }
        } );
        dataGrid.addColumn( openColumn,
                            M2RepoEditorConstants.INSTANCE.Open() );

        dataGrid.addColumnSortHandler( new ColumnSortEvent.AsyncHandler( dataGrid ) );

        initWidget( dataGrid );
    }

    @Override
    public void setContentHeight( String s ) {
        dataGrid.setHeight( s );
    }

    @Override
    public void init( final ArtifactListPresenter presenter ) {
        this.presenter = presenter;
    }

    @Override
    public void addColumn( final Column<JarListPageRow, ?> column,
                           final String caption ) {
        dataGrid.addColumn( column,
                            caption );
    }

    @Override
    public String getCurrentFilter() {
        return currentFilter;
    }

    @Override
    public void setCurrentFilter( final String currentFilter ) {
        this.currentFilter = currentFilter;
    }

    @Override
    public HasData<JarListPageRow> getDisplay() {
        return dataGrid;
    }

    @Override
    public ColumnSortList getColumnSortList() {
        return dataGrid.getColumnSortList();
    }

}
