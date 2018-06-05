package cz.muni.fi.pa165.restClient.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model for table CaloricTable. It defines column names and types.
 */
class CaloricTableModel extends AbstractTableModel {

        private String[] columnNames = {"ID", "Sport activity", "Weight from", "Weight to", "Calory index"};

        public List<Object[]> data = new ArrayList();

        Class[] types = new Class[]{
            java.lang.Long.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
        };

        boolean[] canEdit = new boolean[]{
            false, false, false, false, false
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }

        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }
    }
