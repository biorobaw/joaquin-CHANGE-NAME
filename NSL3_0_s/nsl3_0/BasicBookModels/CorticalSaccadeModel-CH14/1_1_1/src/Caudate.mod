/* SCCS  @(#)Caudate.mod	1.2 --- 09/22/99 -- 23:20:54 */

/*Caudate
* Caudate
* @see Caudate.nslm
* @version 98/6/18
* @author Dominey and Alexander
*
*/
nslImport nslAllImports;

nslModule Caudate (int stdsz)  {
// port inputs
public NslDinFloat2 fefmem(stdsz,stdsz);
public NslDinFloat2 fefsac(stdsz,stdsz);
// port outputs
public NslDoutFloat2 cdmem(stdsz,stdsz);
public NslDoutFloat2 cdsac(stdsz,stdsz);
// parameters 
private NslFloat0 cdmemPot_tm();
private NslFloat0 cdsacPot_tm();
private NslFloat0 cdmemPot_k1();
private NslFloat0 cdsacPot_k1();
private NslFloat0 cdmem_x1();
private NslFloat0 cdmem_x2();
private NslFloat0 cdmem_y1();
private NslFloat0 cdmem_y2();
private NslFloat0 cdsac_x1();
private NslFloat0 cdsac_x2();
private NslFloat0 cdsac_y1();
private NslFloat0 cdsac_y2();

//vars
private NslFloat2 cdmemPot(stdsz,stdsz);
private NslFloat2 cdsacPot(stdsz,stdsz);


public void initRun() {
	cdmem=0;
	cdsac=0;
	cdmemPot_tm= .01 ;
	cdsacPot_tm=  .008;
	cdmemPot_k1= 1 ;
	cdsacPot_k1=  1;	
	cdmem_x1=50 ;
	cdmem_x2=  90;
	cdmem_y1= 0 ;
	cdmem_y2=60  ;
	cdsac_x1=  0;
	cdsac_x2= 50 ;
	cdsac_y1=  0;
	cdsac_y2= 60 ;	
}
public void simRun() {
	cdmemPot=nslDiff(cdmemPot,cdmemPot_tm, - cdmemPot + cdmemPot_k1*fefmem);
	cdsacPot=nslDiff(cdsacPot,cdsacPot_tm,- cdsacPot + cdsacPot_k1*fefsac);
	cdmem = nslSigmoid(cdmemPot,cdmem_x1,cdmem_x2,cdmem_y1,cdmem_y2);
	cdsac = nslSigmoid(cdsacPot,cdsac_x1,cdsac_x2,cdsac_y1,cdsac_y2);

	if (system.debug>22) {
		nslPrintln("Caudate: cdmem");
		nslPrintln( cdmem);
		nslPrintln("Caudate: cdsac");
		nslPrintln(cdsac);

	}
}


}  //end class






