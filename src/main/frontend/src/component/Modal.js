import React, {Component} from "react";

export class Modal extends Component {
    render() {
        const { open, close, header } = this.props;

        return (
            <div className={open ? 'openModal modal' : 'modal'}>
                {open ? (
                    <section>
                        <header>
                            {header}
                        </header>
                        <main>
                            <span className={'modal-span'}>
                                <button className="close" onClick={close}>
                                    &times;
                                </button>
                            </span>
                            {this.props.children}
                        </main>
                    </section>
                ) : null}
            </div>
        );
    }
}