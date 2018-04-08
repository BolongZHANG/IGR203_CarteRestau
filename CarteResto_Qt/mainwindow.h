#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "commandes.h"
#include "produits.h"
#include "produit.h"

#include <QMainWindow>
#include <QWidget>
#include <QMouseEvent>
#include <QVector>

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

private:
    Ui::MainWindow *ui;

    //geometry

    int x,y,w,h;

    Commandes * cmds;
    Produits * produits;
    QVector<QListWidgetItem *> tablesItems;
};

#endif // MAINWINDOW_H
