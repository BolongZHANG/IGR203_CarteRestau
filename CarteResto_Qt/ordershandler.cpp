#include "ordershandler.h"
#include "ui_ordershandler.h"

OrdersHandler::OrdersHandler(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::OrdersHandler)
{
    ui->setupUi(this);

    ui_setup(100,100,500,500);
}

OrdersHandler::OrdersHandler(int x,int y,int w,int h, QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::OrdersHandler)
{
    ui->setupUi(this);

    ui_setup(x,y,w,h);
}

OrdersHandler::~OrdersHandler()
{
    delete ui;
}

void OrdersHandler::ui_setup(int x,int y,int w,int h)
{
    // setting a responsive background image
    this->setStyleSheet(
                "#centralWidget { " /* if we don't do that the following properties will be
                                     * inherited by children causing "Qimage: out of memory, returning null image3
                                     */
                " border-image: url(:images/background.png) 0 0 0 0 stretch stretch;"
                "}");

    /*ui->widgetLogo->setStyleSheet(
                "#widgetLogo { "
                " border-image: url(:images/logo.png) 0 0 0 0 stretch stretch;"
                "}");

    ui->pwLineEdit->setEchoMode(QLineEdit::Password);*/

    this->setGeometry(x,y,w,h);
}

