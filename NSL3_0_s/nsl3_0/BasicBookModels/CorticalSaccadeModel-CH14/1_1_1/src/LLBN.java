/* This file is generated by  NSL3.0 preprocessor*/

/* SCCS  %W% --- %G% -- %U% */

/*LLBN
* Long lead burst neurons
* @see  LLBN.nslm
* @version 98/6/17
* @author Dominey and Alexander
*/
 import nslj.src.system.*; 
 import nslj.src.cmd.*; 
 import nslj.src.lang.*; 
 import nslj.src.math.*; 
 import nslj.src.display.*; 

 public class LLBN extends NslModule /*(int stdsz)*/  {

// ports
 public NslDinFloat2 supcol ; /*(stdsz,stdsz)*/
public NslDinFloat2 fefsac ; /*(stdsz,stdsz)*/
public NslDoutFloat2 llbn ; /* (stdsz,stdsz)*/	// output
// parameters 
private NslFloat0 	llbnPot_tm ; /*()*/
private NslFloat0 	llbnPot_k1 ; /*()*/
private NslFloat0 	llbnPot_k3 ; /*()*/
private NslFloat0 	llbn_kx1 ; /*()*/
private NslFloat0 	llbn_kx2 ; /*()*/
private NslFloat0 	llbn_ky1 ; /*()*/
private NslFloat0 	llbn_ky2 ; /*()*/
//vars
private NslFloat2 llbnwta ; /* (stdsz,stdsz)*/
private NslFloat2 llbnPot ; /* (stdsz,stdsz)*/	// long lead burst neurons of the brainstem saccade generator

private NslFloat0 nWTAThreshold ; /*()*/
private NslFloat0 protocolNum ; /*()*/

public  void initModule() {
	llbnPot_k1.nslSetAccess('W');  // adaptaion factor for lesion FEF
	llbnPot_k3.nslSetAccess('W');  // adaptaion factor for lesion SCS
}

public  void initRun() {
	nWTAThreshold.set((NslFloat0)nslGetValue("domineyModel.nWTAThreshold")) /*rule 114 */;
	protocolNum.set((NslInt0)nslGetValue("domineyModel.protocolNum")) /*rule 114 */;

	llbn.set(0);
	llbnwta.set(0);
	llbnPot.set(0);
        
	llbnPot_tm.set(0.08);
	// aa: From the 92 paper is says that the connection strength
	// from SC to LLBN
	// is increase from 2.67 to 5.0 for 14
	// However this is not in the 2.1.7 stimulus file.
	llbnPot_k1.set(2.67);
	if (
 nslj.src.math.NslEqu.eval(protocolNum.get(),14)) {
		llbnPot_k1.set(5.0); // aa: lesioning of FEF causes
				// SC projections to LLBN to increase
	}
	// aa: From the 92 paper is says that the connection strength
	// from FEF to LLBN
	// is increase from 5.4 to 9.4 for 13
	// However this is not in the 2.1.7 stimulus file.
	llbnPot_k3.set(5.4);
	if (
 nslj.src.math.NslEqu.eval(protocolNum.get(),13)){
		 llbnPot_k3.set(9.4); // aa: lesioning of SC causes 
			// FEF projections to LLBN to increase
	}

	llbn_kx1.set(0);
	llbn_kx2.set(950);
	llbn_ky1.set(0);
	llbn_ky2.set(950); 
}

public  void simRun()
{
	llbnPot.set(system.nsldiff.eval(llbnPot,llbnPot_tm,
 __nsltmp105=nslj.src.math.NslAdd.eval(__nsltmp105,
 __nsltmp103=nslj.src.math.NslAdd.eval(__nsltmp103,
 __nsltmp101=nslj.src.math.NslSub.eval(__nsltmp101,0,llbnPot.get()),
 __nsltmp102=nslj.src.math.NslElemMult.eval(__nsltmp102,llbnPot_k1.get(),supcol.get())),
 __nsltmp104=nslj.src.math.NslElemMult.eval(__nsltmp104,llbnPot_k3.get(),fefsac.get())))) /* rule 108 */;	// 		// visualinput from SC and FEF
	llbnwta.set(DomineyLib.winnerTakeAll(llbnPot,nWTAThreshold.get(),stdsz)) /* rule 108 */;

		// the winner take all is what allows a stimulated
		// saccade to interrupt an ongoing saccade - 
		// implies that weighted averageing occurs upstream

		// note that in the double saccades, the llbnPot (membrane
		// Potential) layer sometimes shows activity at multiple sites

	llbn.set(NslSaturation.eval(llbnwta,llbn_kx1,llbn_kx2,llbn_ky1,llbn_ky2)) /* rule 108 */;

	if (system.debug>=5) {
		System.out.println("debug: LLBN: ");
		System.out.println(llbn);
	}
}
	/* nslInitTempModule inserted by NPP */
public void nslInitTempModule() {
	/* Instantiation statements generated by NslPreProcessor */
	/* temporary variables */
	__nsltmp101 = new float[1][1];
	__nsltmp102 = new float[1][1];
	__nsltmp103 = new float[1][1];
	__nsltmp104 = new float[1][1];
	__nsltmp105 = new float[1][1];
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
	private  float[][] __nsltmp101;
	private  float[][] __nsltmp102;
	private  float[][] __nsltmp103;
	private  float[][] __nsltmp104;
	private  float[][] __nsltmp105;
	/* constructor and related methods */
	/* nsl declarations */
	int stdsz;

	 /*GENERIC CONSTRUCTOR:   */
	 public LLBN(String nslName, NslModule nslParent,int stdsz) {
		super(nslName, nslParent);
		this.stdsz = stdsz;
		initSys();
		makeInst(nslName, nslParent,stdsz);
	}
	public void makeInst(String nslName, NslModule nslParent,int stdsz){ 
	 supcol=new NslDinFloat2 ("supcol",this,stdsz,stdsz); //NSLDECLS 
	 fefsac=new NslDinFloat2 ("fefsac",this,stdsz,stdsz); //NSLDECLS 
	 llbn=new NslDoutFloat2 ("llbn",this,stdsz,stdsz); //NSLDECLS 
	 llbnPot_tm=new NslFloat0 ("llbnPot_tm",this); //NSLDECLS 
	 llbnPot_k1=new NslFloat0 ("llbnPot_k1",this); //NSLDECLS 
	 llbnPot_k3=new NslFloat0 ("llbnPot_k3",this); //NSLDECLS 
	 llbn_kx1=new NslFloat0 ("llbn_kx1",this); //NSLDECLS 
	 llbn_kx2=new NslFloat0 ("llbn_kx2",this); //NSLDECLS 
	 llbn_ky1=new NslFloat0 ("llbn_ky1",this); //NSLDECLS 
	 llbn_ky2=new NslFloat0 ("llbn_ky2",this); //NSLDECLS 
	 llbnwta=new NslFloat2 ("llbnwta",this,stdsz,stdsz); //NSLDECLS 
	 llbnPot=new NslFloat2 ("llbnPot",this,stdsz,stdsz); //NSLDECLS 
	 nWTAThreshold=new NslFloat0 ("nWTAThreshold",this); //NSLDECLS 
	 protocolNum=new NslFloat0 ("protocolNum",this); //NSLDECLS 
	}
	/* end of automatic declaration statements */
}
