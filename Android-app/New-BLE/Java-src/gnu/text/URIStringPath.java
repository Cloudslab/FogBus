package gnu.text;

import java.net.URI;

/* compiled from: URIPath */
class URIStringPath extends URIPath {
    String uriString;

    public String toURIString() {
        return this.uriString;
    }

    public URIStringPath(URI uri, String uriString) {
        super(uri);
        this.uriString = uriString;
    }
}
