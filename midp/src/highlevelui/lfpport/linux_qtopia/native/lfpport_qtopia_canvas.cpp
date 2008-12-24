
#include <QPainter>
#include <QPaintEvent>

#include <lfpport_error.h>
#include <lfpport_canvas.h>
#include <jdisplay.h>

#include "lfpport_qtopia_canvas.h"
#include "lfpport_qtopia_pcsl_string.h"
#include "lfpport_qtopia_debug.h"

// MIDP interface for the JCanvas class
extern "C"
{
  MidpError lfpport_canvas_create(MidpDisplayable* canvasPtr, const pcsl_string* title, const pcsl_string* ticker)
  {
    debug_trace();
    JCanvas *canvas = new JCanvas(JDisplay::current(), canvasPtr,
                                  pcsl_string2QString(*title), pcsl_string2QString(*ticker));
    if (canvas)
      return KNI_OK;
    else
      return KNI_ENOMEM;
  }
}

JCanvas::JCanvas(QWidget *parent, MidpDisplayable *canvasPtr, QString title, QString ticker)
  :JDisplayable(canvasPtr, title, ticker), QWidget(parent)
{

  //widget = static_cast<QWidget *>(this);

  JDisplay::current()->addWidget(this);
}

JCanvas::~JCanvas()
{
}

MidpError JCanvas::j_show()
{
  JDisplay::current()->setCurrentWidget(this);
}

MidpError JCanvas::j_hideAndDelete(jboolean onExit)
{
  delete this;
}

void JCanvas::paintEvent(QPaintEvent *ev)
{
  QPainter p(this);
  p.drawPixmap(ev->rect(), *(JDisplay::current()->backBuffer()), ev->rect());
}

#include "lfpport_qtopia_canvas.moc"