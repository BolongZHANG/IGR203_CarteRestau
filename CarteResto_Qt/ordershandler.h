#ifndef ORDERSHANDLER_H
#define ORDERSHANDLER_H

#include <QMainWindow>

namespace Ui {
class OrdersHandler;
}

class OrdersHandler : public QMainWindow
{
    Q_OBJECT

public:
    explicit OrdersHandler(QWidget *parent = 0);
    explicit OrdersHandler(int x,int y,int w,int h,QWidget *parent = 0);
    ~OrdersHandler();

     void ui_setup(int x,int y,int w,int h);

private:
    Ui::OrdersHandler *ui;
};

#endif // ORDERSHANDLER_H
