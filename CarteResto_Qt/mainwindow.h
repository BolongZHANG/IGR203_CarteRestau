#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "commandes.h"
#include "produits.h"
#include "produit.h"

#include <QMainWindow>
#include <QWidget>
#include <QMouseEvent>
#include <QVector>
#include <QTimer>

#include<QEvent>
#include<QKeyEvent>

#include <QListWidgetItem>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

    void ui_setup();

    QVector<QWidget *> getSectionAndChidlren(int section);
    void setActiveSection(int section);
    void disableSection(int section);
    void setMenuBar();
    void fillTableListWidget();
    void fillAEPDVListsWidgets(  int tableNb  );

    Produit findProduit( double idProduit );

    int getTableNumberFrom( QString s );

    void lanceButtonCode();
    void serviButtonCode();

    void keyPressEvent(QKeyEvent *ev);

public slots:
    void logOut();
    void displayOrdersWidget();
    void displayModifyCardWidget();

    void on_connectionButton_clicked();
    void on_buttonApero_clicked();
    void on_buttonEntree_clicked();
    void on_buttonPlat_clicked();
    void on_buttonDessert_clicked();
    void on_buttonVin_clicked();
    void on_buttonModifier_clicked();

    void on_tablesListWidget_itemClicked( QListWidgetItem * item);

    void on_listWidgetApero_itemClicked( QListWidgetItem * item);
    void on_listWidgetEntrees_itemClicked( QListWidgetItem * item);
    void on_listWidgetPlats_itemClicked( QListWidgetItem * item);
    void on_listWidgetDesserts_itemClicked( QListWidgetItem * item);
    void on_listWidgetVins_itemClicked( QListWidgetItem * item);

    void on_lanceButton1_clicked();
    void on_serviButton1_clicked();

    void on_lanceButton2_clicked();
    void on_serviButton2_clicked();

    void on_lanceButton3_clicked();
    void on_serviButton3_clicked();

    void on_lanceButton4_clicked();
    void on_serviButton4_clicked();

    void on_lanceButton5_clicked();
    void on_serviButton5_clicked();

    void reloadData();

private:
    Ui::MainWindow *ui;

    QTimer * refreshTimer;
    //geometry

    int x,y,w,h;

    Commandes * cmds;
    Produits * produits;
    QVector<QListWidgetItem *> tablesItems;

    QListWidgetItem * selectedProduct;

    bool isReloading = false;
};

#endif // MAINWINDOW_H
