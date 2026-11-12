---
name: gerador-dao
description: inserção em lote com SQLiteStatement, inserção individual opcional com ContentValues e leitura segura de Cursor.
---

# Padrão Obrigatório de Classes DAO (Android Java + SQLite)

Sempre que criar ou atualizar classes no pacote `DAO/`, você **DEVE** seguir estritamente esta arquitetura e fluxo de execução.

## Fluxo Interativo Obrigatório (ANTES de escrever código)
1. **Sempre pergunte ao usuário** antes de gerar ou modificar o DAO:
   > *"Deseja incluir também o método de inserção individual (`salvar<Modelo>`) utilizando `ContentValues` e `db.addDados(...)`?"*
2. Aguarde a resposta do usuário (ou siga o que ele já tiver especificado no prompt inicial) antes de criar/alterar os arquivos.

---

## Regras Estritas
1. **Linguagem:** Estritamente **Java** com `SQLiteDatabase`, `SQLiteStatement`, `ContentValues` e `Cursor`.
2. **Escrita em Lote (`addLista`):**
    - Utilize **sempre** `SQLiteStatement` compilado (`SQL_INSERT`) com `database.beginTransaction()`, `database.setTransactionSuccessful()` e `database.endTransaction()` no `finally`.
    - Utilize exclusivamente os auxiliares `bindString`, `bindInt`, `bindLong` e `bindDouble` para tratar nulos.
3. **Escrita Individual (`salvar<Modelo>` - Quando solicitada):**
    - Utilize `ContentValues` e persista através do método padrão do projeto: `db.addDados("NOME_DA_TABELA", content)`.
    - Retorne o `long rowId` gerado (ou `0` em caso de erro).
    - Trate strings nulas quando necessário (ex: `valor == null ? "" : valor`) e capture `SQLException` e `Exception` com `Log.e`.
4. **Leitura de Cursor (`set<Modelo>`):**
    - Utilize exclusivamente os métodos auxiliares `getString`, `getInt`, `getLong` e `getDouble` (que validam `idx != -1`, `!c.isNull(idx)` e conversões seguras, porem somente os crie se for necessario).
    - Sempre feche o `Cursor` em um bloco `finally`.

---
## Checklist de Execução para o Agente
1. Inspecione a classe POJO em `model/` e a declaração `CREATE TABLE` correspondente em `DBHelper.java`.
2. Pergunte ao usuário sobre a inclusão do método `salvar<Modelo>` (`ContentValues`) caso ele não tenha deixado claro no pedido.
3. Garanta que o número de colunas no `SQL_INSERT` seja exatamente igual à quantidade de `?` e ao maior índice usado nos métodos `bind*`.
4. No método `salvar<Modelo>`, omita colunas `AUTOINCREMENT` (como o `ID` primário local) do `ContentValues` para que o SQLite gere o ID automaticamente.
---

## Template de Referência Obrigatório

```java
package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.rcksuporte05.rcksistemas.model.Modelo;

import java.util.ArrayList;
import java.util.List;

public class ModeloDAO {

    private static final String TAG = "MODELO_DAO";
    private DBHelper db;

    public ModeloDAO(DBHelper db) {
        this.db = db;
    }

    private static final String SQL_INSERT = "INSERT INTO NOME_DA_TABELA (" +
            "COLUNA_1, COLUNA_2, COLUNA_3, COLUNA_4" +
            ") VALUES (?, ?, ?, ?)";

    // =========================================================================
    // 1. INSERÇÃO INDIVIDUAL (ContentValues - Incluir se confirmado pelo usuário)
    // =========================================================================
    public long salvarModelo(Modelo item) {
        ContentValues content = new ContentValues();
        try {
            content.put("COLUNA_1", item.getCampoString() == null ? "" : item.getCampoString());
            content.put("COLUNA_2", item.getCampoInt());
            content.put("COLUNA_3", item.getCampoLong());
            content.put("COLUNA_4", item.getCampoDouble());
            content.put("ST_ENVIADO", "N");
            content.put("EXCLUIDO", "N");

            long rowId = db.addDados("NOME_DA_TABELA", content);
            Log.d(TAG, "salvarModelo -> ROW_ID: " + rowId);
            return rowId;
        } catch (SQLException e) {
            Log.e(TAG, "Erro SQLException ao salvar Modelo: " + e.getMessage(), e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Erro Exception ao salvar Modelo: " + e.getMessage(), e);
            e.printStackTrace();
        }
        return 0;
    }

    // =========================================================================
    // 2. INSERÇÃO EM LOTE (SQLiteStatement + Transação)
    // =========================================================================
    public void addLista(List<Modelo> lista, DBHelper db) {
        SQLiteDatabase database = db.getWritableDatabase();
        SQLiteStatement stmt = database.compileStatement(SQL_INSERT);

        try {
            database.beginTransaction(); // Trava o disco uma única vez

            for (Modelo item : lista) {
                stmt.clearBindings();

                // Vincular campos na ordem exata do SQL_INSERT (iniciando em 1)
                bindString(stmt, 1, item.getCampoString());
                bindInt(stmt, 2, item.getCampoInt());
                bindLong(stmt, 3, item.getCampoLong());
                bindDouble(stmt, 4, item.getCampoDouble());

                stmt.executeInsert();
            }

            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    // =========================================================================
    // 3. CONSULTA E MAPEAMENTO DE CURSOR
    // =========================================================================
    public List<Modelo> getLista() {
        List<Modelo> lista = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM NOME_DA_TABELA", null);
            if (cursor != null && cursor.moveToFirst())
                do {
                    lista.add(setModelo(cursor));
                } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return lista;
    }

    public Modelo getModelo(String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.listaDados(SQL);
            if (cursor != null && cursor.moveToFirst())
                return setModelo(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    private Modelo setModelo(Cursor cursor) {
        Modelo item = new Modelo();
        try {
            item.setCampoString(getString(cursor, "COLUNA_1"));
            item.setCampoInt(getInt(cursor, "COLUNA_2"));
            item.setCampoLong(getLong(cursor, "COLUNA_3"));
            item.setCampoDouble(getDouble(cursor, "COLUNA_4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // =========================================================================
    // 4. AUXILIARES OBRIGATÓRIOS (BINDING & CURSOR NULL-SAFE)
    // =========================================================================
    private void bindString(SQLiteStatement stmt, int i, String val) {
        if (val == null) stmt.bindNull(i); else stmt.bindString(i, val);
    }

    private void bindInt(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindLong(i, Long.parseLong(val.toString()));
    }

    private void bindLong(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindLong(i, Long.parseLong(val.toString()));
    }

    private void bindDouble(SQLiteStatement stmt, int i, Object val) {
        if (val == null) stmt.bindNull(i); else stmt.bindDouble(i, Double.parseDouble(val.toString()));
    }

    private String getString(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx != -1 && !c.isNull(idx)) ? c.getString(idx) : null;
    }

    private int getInt(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0;
        try {
            return Integer.parseInt(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private long getLong(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0L;
        try {
            return Long.parseLong(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private double getDouble(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        if (idx == -1 || c.isNull(idx)) return 0.0;
        try {
            return Double.parseDouble(c.getString(idx));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
```

