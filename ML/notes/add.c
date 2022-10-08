

int add(double* v, int n) {
    double ret = 0;
    for(int i=0;i<n;++i) {
        ret += v[i];
    }
    return ret;

}