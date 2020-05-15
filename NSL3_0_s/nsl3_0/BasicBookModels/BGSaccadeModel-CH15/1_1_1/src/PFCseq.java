/* This file is generated by  NSL3.0 preprocessor*/

/* SCCS  %W%---%G%--%U% */
/* old kversion @(#)PFCseq.mod	1.8 --- 08/05/99 -- 13:56:32 : jversion  @(#)PFCseq.mod	1.2---04/23/99--18:39:40 */

// Import statements

 import nslj.src.system.*; 
 import nslj.src.cmd.*; 
 import nslj.src.lang.*; 
 import nslj.src.math.*; 
 import nslj.src.display.*; 

//
// PFCseq
//

 public class PFCseq extends NslModule/*(int array_size)*/{
    // input ports
    public NslDinDouble2 LIPvis_in ; /*(array_size,array_size)   */
    public NslDinDouble2 PFCmem_in ; /*(array_size,array_size)   */
    public NslDinDouble2 PFCfovea_in ; /*(array_size,array_size)   */
    // output ports
    public NslDoutDouble2 pfcseq_out ; /*(array_size,array_size)   */
    public NslDoutDouble2 PFCseq_out ; /*(array_size,array_size)   */

  // private variables
    private NslDouble2 pfcsel ; /* (array_size,array_size)  */
    private NslDouble2 pfcseq ; /*(array_size,array_size)   */
    private NslInt0 FOVEAX ; /*()*/
    private NslInt0 FOVEAY ; /*()*/


  //NslClass
  private IJpair    tij ; /*()*/
  private  double    pfcseqtm;
  private  double    pfcselK;
  private  double    pfcfoveaK;
  private  double    Refractory;
    private  double SEQmax;
    private  double seqmax;

public  void initModule() {
     tij.init(); //initialize user class
     FOVEAX.set((NslInt0)nslGetValue("crowleyTop.FOVEAX")) /*rule 114 */;
     FOVEAY.set((NslInt0)nslGetValue("crowleyTop.FOVEAY")) /*rule 114 */;
}
  
public  void initRun(){
    pfcseq_out.set(0.0);
    PFCseq_out.set(0.0);

    SEQmax=0;
    seqmax=0;
    
    pfcseqtm = 0.008;
    pfcselK = 1.5;
    pfcfoveaK = 2.0;
    Refractory = 0.025 / system.nslGetRunDelta();
}
public  void simRun(){
     int tempint;

  /// System.err.println("@@@@ PFCseq simRun entered @@@@");

    pfcseq_out.set(SetTargetSequence(LIPvis_in,pfcseq_out)) /* rule 108 */;

    SEQmax=NslMaxValue.eval(PFCseq_out)/* rule 102*/;
    seqmax=NslMaxValue.eval(pfcseq_out)/* rule 102*/;

//    if ((SEQmax < Refractory) && (seqmax > 0.0)){
    if (((NslMaxValue.eval(PFCseq_out))<(Refractory))&&((NslMaxValue.eval(pfcseq_out))>(0.0))){
      tempint=tij.MaxIJ(PFCmem_in)/* rule 102*/;
      pfcsel.set(0.0);
      (pfcsel).set(tij.getI(),tij.getJ(),(PFCmem_in).get(tij.getI(),tij.getJ()));
    }

    PFCseq_out.set(system.nsldiff.eval(PFCseq_out,pfcseqtm,
 __nsltmp105=nslj.src.math.NslSub.eval(__nsltmp105,
 __nsltmp103=nslj.src.math.NslAdd.eval(__nsltmp103,
 __nsltmp101=nslj.src.math.NslSub.eval(__nsltmp101,0,PFCseq_out.get()),
 __nsltmp102=nslj.src.math.NslElemMult.eval(__nsltmp102,pfcselK,pfcsel.get())),
 __nsltmp104=nslj.src.math.NslElemMult.eval(__nsltmp104,pfcfoveaK,PFCfovea_in.get())))) /* rule 108 */;

    pfcseq_out.set(
 __nsltmp106=nslj.src.math.NslElemMult.eval(__nsltmp106,pfcseq_out.get(),0.95));
    //pfcseq = pfcseq * 0.95;
    pfcsel.set(
 __nsltmp107=nslj.src.math.NslElemMult.eval(__nsltmp107,pfcsel.get(),0.95));
}

  // private methods
private  NslDouble2 SetTargetSequence(NslDouble2 inmat, NslDouble2 outmat) {
    // This function set the sequence order for
    // sequentially appearing targets.
     int i, j, savei, savej, imax, jmax;
     int newtarget;
    /*
    imax = (int)inmat.get_imax();
    jmax = (int)inmat.get_jmax();
    */

    imax=(int)inmat.getSize1()/* rule 112 */;
    jmax=(int)inmat.getSize2()/* rule 112 */;

    savei = -1;
    savej = -1;

    newtarget = 0;             //Set to 1 when a saccade target first appears

    for ( i=0; i<imax; i++ ){
      for ( j=0; j<jmax; j++ ){
	if ( (((inmat).get(i,j))>(0.5))&&(((outmat).get(i,j))==(0))){
	  if ( (
 nslj.src.math.NslNeq.eval(i,FOVEAX.get()))||(
 nslj.src.math.NslNeq.eval(j,FOVEAY.get()))){

	    // Found a new target that is not on the fovea.  My assumption
	    // is that there are no saccadic fovea projections from cortex.
	    // Save element location so value can be set to 1 after
	    // all other target memories have been incremented

	    savei = i;
	    savej = j;
	  }
	}
      }
    }
    if ( ( savei >= 0 ) && ( savej >= 0 ) ){
      // New target exists.  Increment any existing target memories.

      for ( i=0; i<imax; i++ ){
	for ( j=0; j<jmax; j++ ){
	  if ( ((outmat).get(i,j))>(0)){
	    (outmat).set(i,j,((outmat).get(i,j))+(1));
	  }
	}
      }
      (outmat).set(savei,savej,1);    //Set new target memory to 1

    }
     return outmat;
}

	/* nslInitTempModule inserted by NPP */
public void nslInitTempModule() {
	/* Instantiation statements generated by NslPreProcessor */
	/* temporary variables */
	__nsltmp101 = new double[1][1];
	__nsltmp102 = new double[1][1];
	__nsltmp103 = new double[1][1];
	__nsltmp104 = new double[1][1];
	__nsltmp105 = new double[1][1];
	__nsltmp106 = new double[1][1];
	__nsltmp107 = new double[1][1];
	/* end of automatic instantiation statements */
	/* Intialisation statements generated by NslPreProcessor */
	/* temporary variables */
	/* end of automatic intialisation statements */
}

	/* nslInitTempRun inserted by NPP */
public void nslInitTempRun() {
	/* Intialisation statements generated by NslPreProcessor */
	/* temporary variables */
	for (int i = 0; i < __nsltmp101.length; i++) {
		for (int j = 0; j < __nsltmp101[0].length; j++) {
			__nsltmp101[i][j] = 0;
		}
	}
	for (int i = 0; i < __nsltmp102.length; i++) {
		for (int j = 0; j < __nsltmp102[0].length; j++) {
			__nsltmp102[i][j] = 0;
		}
	}
	for (int i = 0; i < __nsltmp103.length; i++) {
		for (int j = 0; j < __nsltmp103[0].length; j++) {
			__nsltmp103[i][j] = 0;
		}
	}
	for (int i = 0; i < __nsltmp104.length; i++) {
		for (int j = 0; j < __nsltmp104[0].length; j++) {
			__nsltmp104[i][j] = 0;
		}
	}
	for (int i = 0; i < __nsltmp105.length; i++) {
		for (int j = 0; j < __nsltmp105[0].length; j++) {
			__nsltmp105[i][j] = 0;
		}
	}
	for (int i = 0; i < __nsltmp106.length; i++) {
		for (int j = 0; j < __nsltmp106[0].length; j++) {
			__nsltmp106[i][j] = 0;
		}
	}
	for (int i = 0; i < __nsltmp107.length; i++) {
		for (int j = 0; j < __nsltmp107[0].length; j++) {
			__nsltmp107[i][j] = 0;
		}
	}
	/* end of automatic intialisation statements */
}

	/* nslInitTempTrain inserted by NPP */
public void nslInitTempTrain() {
	/* Initialisation statements generated by NslPreProcessor */
	/* temporary variables */
	/* end of automatic intialisation statements */
}

	/* Declaration statements generated by NslPreProcessor */
	/* makeinst() declared variables */
	/* temporary variables */
	private  double[][] __nsltmp101;
	private  double[][] __nsltmp102;
	private  double[][] __nsltmp103;
	private  double[][] __nsltmp104;
	private  double[][] __nsltmp105;
	private  double[][] __nsltmp106;
	private  double[][] __nsltmp107;
	/* constructor and related methods */
	/* nsl declarations */
	int array_size;

	 /*GENERIC CONSTRUCTOR:   */
	 public PFCseq(String nslName, NslModule nslParent,int array_size) {
		super(nslName, nslParent);
		this.array_size = array_size;
		initSys();
		makeInst(nslName, nslParent,array_size);
	}
	public void makeInst(String nslName, NslModule nslParent,int array_size){ 
	 LIPvis_in=new NslDinDouble2 ("LIPvis_in",this,array_size,array_size); //NSLDECLS 
	 PFCmem_in=new NslDinDouble2 ("PFCmem_in",this,array_size,array_size); //NSLDECLS 
	 PFCfovea_in=new NslDinDouble2 ("PFCfovea_in",this,array_size,array_size); //NSLDECLS 
	 pfcseq_out=new NslDoutDouble2 ("pfcseq_out",this,array_size,array_size); //NSLDECLS 
	 PFCseq_out=new NslDoutDouble2 ("PFCseq_out",this,array_size,array_size); //NSLDECLS 
	 pfcsel=new NslDouble2 ("pfcsel",this,array_size,array_size); //NSLDECLS 
	 pfcseq=new NslDouble2 ("pfcseq",this,array_size,array_size); //NSLDECLS 
	 FOVEAX=new NslInt0 ("FOVEAX",this); //NSLDECLS 
	 FOVEAY=new NslInt0 ("FOVEAY",this); //NSLDECLS 
	 tij=new IJpair ("tij",this); //NSLDECLS 
	}
	/* end of automatic declaration statements */
} //end class
