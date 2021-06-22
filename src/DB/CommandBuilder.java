package DB;

/**
 * Builder provides capability to create requests to SQL DB using SQL enum as bricks
 */
public class CommandBuilder {
    public StringBuffer command = new StringBuffer();

    public StringBuffer build() {
        return command;
    }

    public CommandBuilder marker(String marker) {
        command.append(marker);
        return this;
    }

    public CommandBuilder select(SQL select) {
        command.append("SELECT " + select.getText() + " ");
        return this;
    }

    public CommandBuilder select() {
        command.append("SELECT * ");
        return this;
    }

    public CommandBuilder insert(SQL tableName, SQL[] columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            sb.append(columns[i].getText());
            if (i != columns.length - 1) sb.append(", ");
        }
        char c = '?';
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            sb2.append(c);
            if (i != columns.length - 1) sb2.append(", ");
        }
        command.append("INSERT INTO " + tableName.getText() + " (" + sb + ") VALUES (" + sb2 + ") ");
        return this;
    }

    public CommandBuilder update(SQL tableName, SQL column) {
        command.append("UPDATE " + tableName.getText() + " SET " + column.getText() + " = ? ");
        return this;
    }

    public CommandBuilder update(SQL tableName, SQL[] columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            sb.append(columns[i].getText() + " = ?");
            if (i != columns.length - 1) sb.append(", ");
        }
        command.append("UPDATE " + tableName.getText() + " SET " + sb + " ");
        return this;
    }

    public CommandBuilder delete() {
        command.append("DELETE ");
        return this;
    }

    public CommandBuilder delete(SQL tableName) {
        command.append("DELETE FROM " + tableName.getText() + " ");
        return this;
    }

    public CommandBuilder values(int num) {
        char c = '?';
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(c);
            if (i != num - 1) sb.append(", ");
        }
        command.append("VALUES (" + sb + ") ");
        return this;
    }

    public CommandBuilder from(SQL from) {
        command.append("FROM " + from.getText() + " ");
        return this;
    }



    public CommandBuilder where(SQL column) {
        command.append("WHERE " + column.getText() + " = ? ");
        return this;
    }

    public CommandBuilder where(SQL column, String symbol) {
        command.append("WHERE "+ column.getText() + " " + symbol +" ? ");
        return this;
    }

    public CommandBuilder and(SQL column) {
        command.append("AND "+ column.getText() + " = ? ");
        return this;
    }

    public CommandBuilder and(SQL column, String symbol) {
        command.append("AND "+ column.getText() + " " + symbol +" ? ");
        return this;
    }

    public CommandBuilder or(SQL column) {
        command.append("OR " + column.getText() + " = ? ");
        return this;
    }

    public CommandBuilder or(SQL column, String symbol) {
        command.append("OR "+ column.getText() + " " + symbol +" ? ");
        return this;
    }

    public CommandBuilder limit() {
        command.append("LIMIT 1 ");
        return this;
    }

    public CommandBuilder limit(int num) {
        command.append("LIMIT " + num + " ");
        return this;
    }

    public CommandBuilder innerJoin(SQL table, SQL column1, SQL column2) {
        command.append("INNER JOIN " + table.getText() +" ON " + column1.getText() + " = " + column2.getText() + " ");
        return this;
    }
}


