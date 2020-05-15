/* This file is generated by  NSL3.0 preprocessor*/

/* SCCS  %W%---%G%--%U% */
/* old kversion @(#)SCqv.mod	1.8 --- 08/05/99 -- 13:56:35 : jversion  @(#)SCqv.mod	1.2---04/23/99--18:39:47 */

// --------------------------------- SCqv layer -----------------------------

 import nslj.src.system.*; 
 import nslj.src.cmd.*; 
 import nslj.src.lang.*; 
 import nslj.src.math.*; 
 import nslj.src.display.*; 

//LNK_SC3
/**
* Here is the class representing the target locations for SRBNs (saccade
* related burst neurons). This layer is called in the model quasi visual
* layer (SCqv) layer. The SCqv cells receive their input from LIP.
*/

 public class SCqv extends NslModule /*(int array_size)*/ {

//input ports
  NslDinDouble2 LIPmem_in ; /*(array_size,array_size)  */
//output ports
  NslDoutDouble2 SCqv_out ; /* (array_size,array_size)        */

//privates

private  NslDouble2 scqv ; /* (array_size,array_size)  */
private  double SCqvsigma1;
private  double SCqvsigma2;
private  double SCqvsigma3;
private  double SCqvsigma4;
private  double scqvtm;

public  void initRun(){
  scqv.set(0);
  SCqv_out.set(0);

  SCqvsigma1 =   0;
  SCqvsigma2 =  90;
  SCqvsigma3 =   0;
  SCqvsigma4 =  90;
  scqvtm = 0.01;
}
public  void simRun(){
  // System.err.println("@@@@ SCqv simRun entered @@@@");
  scqv.set(system.nsldiff.eval(scqv,scqvtm,
 __nsltmp102=nslj.src.math.NslAdd.eval(__nsltmp102,
 __nsltmp101=nslj.src.math.NslSub.eval(__nsltmp101,0,scqv.get()),LIPmem_in.get()))) /* rule 108 */;
  SCqv_out.set(Nsl2Sigmoid.eval(scqv,SCqvsigma1,SCqvsigma2,SCqvsigma3,SCqvsigma4)) /* rule 108 */;
}

	/* nslInitTempModule inserted by NPP */
public void nslInitTempModule() {
	/* Instantiation statements generated by NslPreProcessor */
	/* temporary variables */
	__nsltmp101 = new double[1][1];
	__nsltmp102 = new double[1][1];
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
	/* constructor and related methods */
	/* nsl declarations */
	int array_size;

	 /*GENERIC CONSTRUCTOR:   */
	 public SCqv(String nslName, NslModule nslParent,int array_size) {
		super(nslName, nslParent);
		this.array_size = array_size;
		initSys();
		makeInst(nslName, nslParent,array_size);
	}
	public void makeInst(String nslName, NslModule nslParent,int array_size){ 
	 LIPmem_in=new NslDinDouble2 ("LIPmem_in",this,array_size,array_size); //NSLDECLS 
	 SCqv_out=new NslDoutDouble2 ("SCqv_out",this,array_size,array_size); //NSLDECLS 
	 scqv=new NslDouble2 ("scqv",this,array_size,array_size); //NSLDECLS 
	}
	/* end of automatic declaration statements */
} //end class
