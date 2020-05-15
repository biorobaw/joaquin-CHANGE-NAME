/* SCCS  @(#)Element.mod	1.1---09/24/99--18:57:16 */
/* old kversion @(#)Element.mod	1.8 --- 08/05/99 -- 13:56:16 */
verbatim_NSLJ;

import java.util.Random;

class Element
{
    int x, y, xo, yo;
    Element next;
static   Random rand = new Random(599);
static final int FOVEAX = 4;
static final int FOVEAY = 4;
  static final int CorticalArraySize = 9;
  static final int StriatalArraySize = 90;
  static final int MaxConnections = 50;
  static final int MaxCenters = 16;

  Element(String name) { this();}
  Element() {
    x=-1; y=-1; xo=-1; yo=-1; next=null;}

  Element(int max) {
   do {
      x = rand.nextInt()%max;
      y = rand.nextInt()%max;
//  Make sure Element location is not the fovea

    } while (x==FOVEAX && y==FOVEAY);
    do {
      xo = rand.nextInt()%max;
      yo = rand.nextInt()%max;
//  Make sure the offset saccade Element is not the fovea or the same as the
//  saccade Element.
    } while ((xo==FOVEAX && yo==FOVEAY) ||
	     (xo == x && yo == y));
    next = null;
  }
Element( int tx, int ty, int txo, int tyo )
{
    x = tx;
    y = ty;
    xo = txo;
    yo = tyo;
    next = null;
}

void AddElement( int max ) {

    Element cur, last, temp = new Element( max );

    cur = next; 
    last = null;

//  Find the end of the list of Elements so we can add another one

    while ( cur != null ){
        last = cur;
        cur = cur.next;
    }

    if ( last == null )
        next = temp;
    else
        last = temp;
}
Element AddRandomElements( int max, int count )
{
    Element last, temp, first;
    int i;

    first = null;
    last  = null;

//  Create the new list of Elements and then Merge to existing list

    for ( i=0; i<count; i++ ) {
        temp = new Element( max );

        if ( first == null ) 
            first = temp;
        else
            last.next = temp;

        last = temp;
        last.next = null;
    }

//  Call the Merge member function to append the new list of Elements
//  to the existing list of Elements

    if ( this == null )
        return first;
    else
        this.Merge( first );

    return this;
}

Element AddSpecifiedElements( int max, int tx, int ty, int txo, int tyo) {
  
  Element first = new Element( tx, ty, txo, tyo );

//  Call the Merge member function to append the new list of Elements
//  to the existing list of Elements

    if ( this == null )
        return first;
    else
        this.Merge( first );

    return this;
}

Element AddSpecifiedElements( int max, int count )
{
    Element last, temp, first;
    int i, tx, ty, txo, tyo, tsize;

    tsize = CorticalArraySize;

    first = null;
    last  = null;

//  Create the new list of Elements and then Merge to existing list

    for ( i=0; i<count; i++ )    {
//      Prompt for the coordinates for the specified Element

        tx = nslj.src.system.Console.readInt("Enter x coordinates for first target (range:0-"
                  +(CorticalArraySize-1)+"): ");
        ty = nslj.src.system.Console.readInt("Enter y coordinates for first target (range:0-"
                  +(CorticalArraySize-1)+"): ");
        txo = nslj.src.system.Console.readInt("Enter x coordinates for second target (range:0-"
                  +(CorticalArraySize-1)+"): ");
        tyo = nslj.src.system.Console.readInt("Enter y coordinates for second target (range:0-"
                  +(CorticalArraySize-1)+"): ");

        if ( ( tx < 0 )  || ( tx >  CorticalArraySize-1) ||
             ( ty < 0 )  || ( ty >  CorticalArraySize-1) ||
             ( txo < 0 ) || ( txo > CorticalArraySize-1) ||
             ( tyo < 0 ) || ( tyo > CorticalArraySize-1) ) {

            System.out.println("Coordinates out of range.  Try again.");
	}
        else {

//          Create the Element and put on the new list

            temp = new Element( tx, ty, txo, tyo );

            if ( first == null ) 
                first = temp;
            else
                last.next = temp;

            last = temp;
            last.next = null;
	}
    }

//  Call the Merge member function to append the new list of Elements
//  to the existing list of Elements

    if ( this == null )
        return first;
    else
        this.Merge( first );

    return this;
}

Element Merge( Element list ) {
    Element cur, last;

    if ( x == -1 ) {
      x = list.x;
      y = list.y;
      xo = list.xo;
      yo = list.yo;
      next = list.next;
      return this;
    }

    cur  = next; 
    last = null;

    while ( cur != null ){
        last = cur;
        cur  = cur.next;
    }

    if ( last == null )
        next = list;
    else
        last.next = list;

    return this;
}

void Remove( )
{

    Element cur, last;
    last = this;
    cur = next;
    while ( cur != null ) {
      last.next = null;
      last = cur;
      cur = cur.next;
    }
    x = -1;
    y = -1;
    xo  = -1;
    yo = -1;
}

double Check( int i, int j ) {
//Checks the input i,j Element location to see if it is a correct
//remapped location.  If so, returns 0, if not returns -1/2*LearningRate.

    if ( ( i == x  ) && ( j == y  ) ) return 0.0;
    if ( ( i == xo ) && ( j == yo ) ) return 0.0;
return -0.0025;
    //    return ( -0.5 * LearnRate.elem() ) ;
}

void Remap( int max, Element elem ) {

//  This function "remaps" the calling Element and returns an Element
//  containing the remapped location.

    int xt, yt, xot, yot;

    xt = FOVEAX - x; yt = FOVEAY - y;
    xot = xt + xo;   yot = yt + yo;

    elem.x = FOVEAX; elem.y = FOVEAY;

    if ( ( xot > -1 ) && ( xot < max ) )
        elem.xo = xot;
    else
        elem.xo = -1;

    if ( ( yot > -1 ) && ( yot < max ) )
        elem.yo = yot;
    else
        elem.yo = -1;

    return;
}

Element GetElement( )
{
// This method displays a list of all Elements, prompts the user
// for which one they want and returns the pointer of the selected Element

    Element cur;
    int     counter, selection;

    cur     = this;
    counter = 0;

//  Display the list of Elements

    System.out.println("\n\n      X    Y  XO  YO\n");

    while ( cur != null ) {
        counter++;
	System.out.println(counter+","+ cur.X()+"  "+ cur.Y()+"  "+
                                            cur.XO()+"  "+cur.YO() );
        cur = cur.Next();
    }

//  Ask for the selected Element

    selection = 0;

    while ( ( selection < 1 ) || ( selection > counter ) ) {
        selection = nslj.src.system.Console.readInt("Enter the number of the selected sequence ");

        if ( ( selection < 1 ) || ( selection > counter ) )
            System.out.println("\nInvalid choice");

    }

//  Get the selected Element and return its pointer

    cur = this;

    for( counter=1; counter<selection; counter++ )
        cur = cur.Next();

    return cur;
}

public String toString() {
  Element telem;

  if (x == -1) 
    return "no Element to display";

  StringBuffer strbuf = new StringBuffer(1024);
  telem = this;
  strbuf.append("X\tY\tXO\tYO");
  while (telem!=null) {
    strbuf.append( x+"\t"+y+"\t"+xo+"\t"+yo);
  }
  return strbuf.toString();
}


  protected void finalize() { next = null;}

  Element Next() {return next;}
  int X() {return x;}
  int Y() {return y;}
  int XO() {return xo;}
  int YO() {return yo;}

}
verbatim_off;
