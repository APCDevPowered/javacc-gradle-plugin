package org.apcdevpowered.gradle.javacc.model

import org.gradle.api.tasks.compile.AbstractOptions

class JTBOptions extends AbstractOptions {
    boolean cl // Print a list of the classes generated to standard out.
    String d // "-d dir" is short for (and overwrites) "-nd dir/syntaxtree -vd dir/visitor".
    boolean dl // Generate depth level info.
    boolean e // Suppress JTB semantic error checking.
    boolean f // Use descriptive node class field names.
    boolean h // Display this help message and quit.
    boolean ia = true // Inline visitors accept methods on base classes.
    boolean jd = true // Generate JavaDoc-friendly comments in the nodes and visitor.
    String nd // Use dir as the package for the syntax tree nodes.
    String np // Use pkg as the package for the syntax tree nodes.
    String ns // Use class as the class which all node classes will extend.
    String o // Use file as the filename for the annotated output grammar.
    String p // "-p pkg" is short for (and overwrites) "-np pkg.syntaxtree -vp pkg.visitor".
    boolean pp // Generate parent pointers in all node classes.
    boolean printer // Generate a syntax tree dumping visitor.
    boolean si // Read from standard input rather than a file.
    boolean scheme // Generate Scheme records representing the grammar and a Scheme tree building visitor.
    boolean tk = true // Generate special tokens into the tree.
    String vd // Use dir as the package for the default visitor classes.
    String vp // Use pkg as the package for the default visitor classes.
    boolean w // Do not overwrite existing files.
    boolean isCl() {
        return cl
    }

    void setCl(boolean cl) {
        this.cl = cl
    }

    String getD() {
        return d
    }

    void setD(String d) {
        this.d = d
    }

    boolean isDl() {
        return dl
    }

    void setDl(boolean dl) {
        this.dl = dl
    }

    boolean isE() {
        return e
    }

    void setE(boolean e) {
        this.e = e
    }

    boolean isF() {
        return f
    }

    void setF(boolean f) {
        this.f = f
    }

    boolean isH() {
        return h
    }

    void setH(boolean h) {
        this.h = h
    }

    boolean isIa() {
        return ia
    }

    void setIa(boolean ia) {
        this.ia = ia
    }

    boolean isJd() {
        return jd
    }

    void setJd(boolean jd) {
        this.jd = jd
    }

    String getNd() {
        return nd
    }

    void setNd(String nd) {
        this.nd = nd
    }

    String getNp() {
        return np
    }

    void setNp(String np) {
        this.np = np
    }

    String getNs() {
        return ns
    }

    void setNs(String ns) {
        this.ns = ns
    }

    String getO() {
        return o
    }

    void setO(String o) {
        this.o = o
    }

    String getP() {
        return p
    }

    void setP(String p) {
        this.p = p
    }

    boolean isPp() {
        return pp
    }

    void setPp(boolean pp) {
        this.pp = pp
    }

    boolean isPrinter() {
        return printer
    }

    void setPrinter(boolean printer) {
        this.printer = printer
    }

    boolean isSi() {
        return si
    }

    void setSi(boolean si) {
        this.si = si
    }

    boolean isScheme() {
        return scheme
    }

    void setScheme(boolean scheme) {
        this.scheme = scheme
    }

    boolean isTk() {
        return tk
    }

    void setTk(boolean tk) {
        this.tk = tk
    }

    String getVd() {
        return vd
    }

    void setVd(String vd) {
        this.vd = vd
    }

    String getVp() {
        return vp
    }

    void setVp(String vp) {
        this.vp = vp
    }

    boolean isW() {
        return w
    }

    void setW(boolean w) {
        this.w = w
    }

    String[] buildOptions() {
        List<String> options = new ArrayList<String>()
        if(cl) { options.add('-cl') }
        if(d) { options.add('-d') options.add(d) }
        if(dl) { options.add('-dl') }
        if(e) { options.add('-f') }
        if(h) { options.add('-h') }
        if(ia) { options.add('-ia') }
        if(jd) { options.add('-jd') }
        if(nd) { options.add('-nd') options.add(nd) }
        if(np) { options.add('-np') options.add(np) }
        if(ns) { options.add('-ns') options.add(ns) }
        if(o) { options.add('-o') options.add(o) }
        if(p) { options.add('-p') options.add(p) }
        if(pp) { options.add('-pp') }
        if(printer) { options.add('-printer') }
        if(si) { options.add('-si') }
        if(scheme) { options.add('-scheme') }
        if(tk) { options.add('-tk') }
        if(vd) { options.add('-vd') options.add(vd) }
        if(vp) { options.add('-vp') options.add(vp) }
        return options
    }
}
